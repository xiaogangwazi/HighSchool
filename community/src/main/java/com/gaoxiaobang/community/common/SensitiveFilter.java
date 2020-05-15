package com.gaoxiaobang.community.common;

import com.alibaba.druid.sql.visitor.functions.Char;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.java.Log;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@Component
@Log
public class SensitiveFilter {

    private TrieNode root= new TrieNode(null);//根节点

    /**
     * 初始化方法，初始化字典树
     */
    public SensitiveFilter(){
    }
    @PostConstruct
    public void init(){

        try(
                InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("sensitiveWord.txt");

        ) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String curr=null;
            while((curr=bufferedReader.readLine())!=null&& !StringUtils.isBlank(curr)){
                System.out.println(curr);
                addWord(curr);
            }
        }catch (IOException e){
            log.warning("加载敏感词文件失败"+e.getMessage());
        }
    }

    public String filter(String text){
        boolean haslast=false;
        if(StringUtils.isBlank(text)){
            return null;
        }
        TrieNode tem=root;
        int begin =0;
        int position=0;
        StringBuffer stringBuffertext= new StringBuffer(text);

        StringBuffer stringBuffer = new StringBuffer();

        while (begin>=0&&begin<stringBuffertext.length()){
               TrieNode child=null;
               if(begin==position&&isSymbol(stringBuffertext.charAt(position))){
                   if (!haslast) {
                       stringBuffer.append(stringBuffertext.charAt(position));
                   }else {
                       haslast=false;
                   }
                   position=++begin;
                    continue;
               }
               if(isSymbol(stringBuffertext.charAt(position))){
                   position++;
                   continue;
               }
               if((child=tem.getChildNode(stringBuffertext.charAt(position)))==null){
                   if (!haslast) {
                       stringBuffer.append(stringBuffertext.charAt(begin));

                   }else {
                       haslast=false;
                   }
                  position=++begin;
                  tem=root;
               }else{
                   if(child.isEnd()){
                       if (stringBuffer.length()==0||stringBuffer.charAt(stringBuffer.length()-1)!='*') {
                               stringBuffer.append("***");

                       }
                       stringBuffertext.replace(begin,position+1,"");
                       position=--begin;
                       tem=root;
                       haslast=true;

                   }else {
                       tem=child;
                       position++;
                   }
               }
           }
           if (begin>=0&&begin<stringBuffertext.length())
        stringBuffer.append(stringBuffertext.substring(begin));
        return stringBuffer.toString();
    }
    public boolean isSymbol(Character character){
        return !CharUtils.isAsciiAlphanumeric(character)&&(character<0x2E80||character>0x9FFF);
    }

    public void addWord(String word){
            TrieNode temp= root;
            for(int i=0;i<word.length();i++){
                char c= word.charAt(i);
                if(temp.containsChild(c)){
                    temp=temp.getChildNode(c);
                }else {
                    temp.addChildNode(c);
                    temp=temp.getChildNode(c);
                }
                if (i==word.length()-1){
                    temp.setEnd(true);
                }
            }
    }


    private class TrieNode{
        private Character value;
        private Map<Character,TrieNode> childNodes= new HashMap<>();
        private boolean end;

        public TrieNode(Character character){
            this.value=character;
        }
        public Character getValue() {
            return value;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }
        public boolean isEnd(){
            return end;
        }
        public void addChildNode(Character c){
                childNodes.put(c,new TrieNode(c));
        }
        public boolean containsChild(Character c){
            return childNodes.containsKey(c);
        }
        public TrieNode getChildNode(Character character){
            return childNodes.get(character);
        }
    }
}
