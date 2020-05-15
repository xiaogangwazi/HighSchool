package com.gaoxiaobang.community.common;

import com.gaoxiaobang.community.common.exception.PageFlagNullException;
import com.gaoxiaobang.community.common.exception.PageSizeNullException;
import lombok.extern.java.Log;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;

import java.sql.SQLException;
import java.util.*;

/**
 * 步骤：1.根据id后缀查看是否是需要拦截的方法
 *       2.获取原sql，构建分页sql
 *       3.
 */
@Log
@Intercepts({
        @Signature(type = Executor.class,method = "query",args ={MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class,method = "query",args ={MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        })
public class PagerInterceptor1 implements Interceptor {
    private String PAGE_FLAG;//分页后缀
    private int pageSize;//分页大小
    private static final ObjectFactory OBJECT_FACTORY=new DefaultObjectFactory();
    private static final ObjectWrapperFactory OBJECT_WRAPPER_FACTORY= new DefaultObjectWrapperFactory();
    private static  final ReflectorFactory REFLECTOR_FACTORY= new DefaultReflectorFactory();
    private static  final List<ResultMapping> DEFAULT_LIST_RESULTMAPPING= new ArrayList<>();
    private static final String COUNT_FLAG="_COUNT_FLAG";



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Executor executor = (Executor)invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        Object parameter=args[1];
        RowBounds rowBounds=(RowBounds) args[2];
        ResultHandler resultHandlers=(ResultHandler) args[3];
        if(mappedStatement.getId().endsWith(PAGE_FLAG)){
            log.info("分页查询");
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql();

            Object parameterObject = boundSql.getParameterObject();
            if(parameterObject!=null) {
                Page pageInfoFromParameter = getPageInfoFromParameter(parameter);
                String buildPageSql = buildPageSql(sql, pageInfoFromParameter);
                long totalCount = getTotalCount(executor, mappedStatement, parameter, boundSql, resultHandlers);
                pageInfoFromParameter.setTotal((int)totalCount);
                int pageCount=(int)Math.ceil((double) totalCount/pageSize);
                pageInfoFromParameter.setPageTotal(pageCount);
                pageInfoFromParameter.setPageSize(pageSize);
                resetSql2Invocation(invocation,buildPageSql);
             }
        }
        return invocation.proceed();
    }

        /**
         * 构造count查询语句，创建新的boundSql，创建新的mappedStatement，再通过executor执行查询
         * @param executor
         * @param mappedStatement
         * @param parameter
         * @param boundSql
         * @param resultHandler
         * @return
         * @throws SQLException
         */
    public Long getTotalCount(Executor executor,MappedStatement mappedStatement,Object parameter,BoundSql boundSql,ResultHandler resultHandler) throws SQLException {
       String sql = boundSql.getSql();
        StringBuffer stringBuffer  = new StringBuffer();
        stringBuffer.append("select count(1) from (").append(sql).append(") as temp");
        BoundSql newboundSql = new BoundSql(mappedStatement.getConfiguration(),stringBuffer.toString(),boundSql.getParameterMappings(),parameter);
        MappedStatement mappedStatement1 = buildCountMappedStatement(mappedStatement, mappedStatement.getId()+COUNT_FLAG);
        CacheKey cacheKey = executor.createCacheKey(mappedStatement1, parameter, RowBounds.DEFAULT, boundSql);
        List query = executor.query(mappedStatement1, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, newboundSql);
        return (Long)query.get(0);
    }
    public void setProperties(MappedStatement.Builder builder,MappedStatement mappedStatement){
        builder.fetchSize(mappedStatement.getFetchSize());
        builder.cache(mappedStatement.getCache());
        builder.flushCacheRequired(mappedStatement.isFlushCacheRequired());
        builder.keyGenerator(mappedStatement.getKeyGenerator());
        builder.resource(mappedStatement.getResource());
        builder.resultMaps(mappedStatement.getResultMaps());
        builder.parameterMap(mappedStatement.getParameterMap());
        builder.statementType(mappedStatement.getStatementType());
        builder.resultSetType(mappedStatement.getResultSetType());
        builder.useCache(mappedStatement.isUseCache());
        builder.databaseId(mappedStatement.getDatabaseId());
        builder.resultOrdered(mappedStatement.isResultOrdered());
        StringBuffer stringBuffer = new StringBuffer();
        if(mappedStatement.getKeyProperties()!=null) {
            for (String s : mappedStatement.getKeyProperties()) {
                stringBuffer.append(s).append(",");
            }
            String s = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length()).toString();
            builder.keyProperty(s);
        }
    }

    /**
     * 创建新的mappedStatement，添加结果映射
     * @param mappedStatement
     * @param id
     * @return
     */
    public MappedStatement buildCountMappedStatement(MappedStatement mappedStatement,String id){
            MappedStatement.Builder builder = new MappedStatement.Builder(mappedStatement.getConfiguration(),id,mappedStatement.getSqlSource(),mappedStatement.getSqlCommandType());

            List<ResultMap> resultMapList = new ArrayList<>();
            ResultMap.Builder resultMap =  new ResultMap.Builder(mappedStatement.getConfiguration(),id,Long.class,DEFAULT_LIST_RESULTMAPPING);
            resultMapList.add(resultMap.build());
            builder.resultMaps(resultMapList);
            return builder.build();
        }

    /**
     * 利用mybatis提供的反射工具，将sql注入到mappedStatement中去
     * @param invocation
     * @param sql
     */
    public void resetSql2Invocation(Invocation invocation,String sql){
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatements = (MappedStatement) args[0];
        Object parameter= args[1];
        MappedStatement mappedStatement = buildMappedStatement(mappedStatements, mappedStatements.getBoundSql(parameter));
        MetaObject metaObject = MetaObject.forObject(mappedStatement, OBJECT_FACTORY, OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
        metaObject.setValue("sqlSource.boundSql.sql",sql);
        args[0]=mappedStatement;
    }

    /**
     * 构建新的mappedStatement
     * @param mappedStatement
     * @param boundSql
     * @return
     */
    public MappedStatement buildMappedStatement(MappedStatement mappedStatement,BoundSql boundSql){
        MappedStatement.Builder builder = new MappedStatement.Builder(mappedStatement.getConfiguration(),mappedStatement.getId(),new BoundSqlSource(boundSql),mappedStatement.getSqlCommandType());
       setProperties(builder,mappedStatement);
        return builder.build();
    }

    /**
     * 构建分页sql
     * @param sql
     * @param page
     * @return
     */
    public  String buildPageSql(String sql,Page page){
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(sql);
                stringBuffer.append("  limit ").append(String.valueOf(page.getCurr()*pageSize)).append(",").append(pageSize);
                return stringBuffer.toString();
    }

    /**
     * 解析parameter
     * @param parameter
     * @return
     */
    public Page getPageInfoFromParameter(Object parameter){
        Page page=null;
            if(parameter instanceof Page){
                page=(Page)parameter;
            }else  if(parameter instanceof Map){
                for(Map.Entry entry: (Set<Map.Entry>)((Map) parameter).entrySet()){
                        if (entry.getValue() instanceof Page){
                            page=(Page) entry.getValue();
                            break;
                        }
                }
            }
            return page;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }



    @Override
    public void setProperties(Properties properties)  {
        Integer o = Integer.valueOf((String) properties.get("pager.pageSize"));
        if(o==null){
            try {
                throw  new PageSizeNullException("pageSize can not be null");
            } catch (PageSizeNullException e) {
                e.printStackTrace();
            }
        }
        pageSize=o;
        String o1 = (String) properties.get("pager.pageFlag");
        if(o1==null){
            try {
                throw  new PageFlagNullException("pageFlag can not be null.");
            } catch (PageFlagNullException e) {
                e.printStackTrace();
            }
        }
        PAGE_FLAG=o1;

    }
    class BoundSqlSource implements SqlSource{
            private BoundSql boundSql;
            BoundSqlSource(BoundSql boundSql){
                this.boundSql=boundSql;
            }
        @Override
        public BoundSql getBoundSql(Object o) {
            return boundSql;
        }
    }
}
