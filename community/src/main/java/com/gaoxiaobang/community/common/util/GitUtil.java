package com.gaoxiaobang.community.common.util;

import lombok.extern.java.Log;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.HttpConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Log
public class GitUtil {


    public static CredentialsProvider createCredential(String userName, String password) {
        return new UsernamePasswordCredentialsProvider(userName, password);
    }

    /**
     * clone命令
     * @param repoUrl
     * @param cloneDir
     * @param provider
     * @return
     * @throws GitAPIException
     */
    public static Git fromCloneRepository(String repoUrl, String cloneDir, CredentialsProvider provider) throws GitAPIException {
        Git git = Git.cloneRepository()
                .setCredentialsProvider(provider)
                .setURI(repoUrl)
                .setDirectory(new File(cloneDir)).call();
        return git;
    }

    /**
     * commit命令
     * @param git
     * @param message
     * @param provider
     * @throws GitAPIException
     */
    public static void commit(Git git, String message, CredentialsProvider provider) throws GitAPIException {
        git.add().addFilepattern(".").call();
        CommitCommand commit = git.commit();
        commit.setCredentialsProvider(provider);
        commit.setMessage(message);
        commit.call();
    }

    /**
     * push命令
     * @param git
     * @param provider
     * @throws GitAPIException
     * @throws IOException
     */
    public static void push(Git git, CredentialsProvider provider) throws  Exception {
        try {
            push(git, "master", provider);
        }catch (Exception e){
            log.warning("git 上传是失败："+e.getMessage());
            throw e;
        }
    }

    /**
     * push 指定分支
     * @param git
     * @param branch
     * @param provider
     * @throws GitAPIException
     * @throws IOException
     */
    public static void push(Git git, String branch, CredentialsProvider provider) throws GitAPIException, IOException {
        if (branch == null) {
            branch = git.getRepository().getBranch();
        }
        git.push()
                .setCredentialsProvider(provider)
                .setRemote("origin").setRefSpecs(new RefSpec(branch)).call();
    }

    /**
     * 读取已经克隆项目
     * @param dir
     * @return
     * @throws IOException
     */
    public static Repository getRepositoryFromDir(String dir) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(Paths.get(dir, ".git").toFile())
                .build();
    }

    /**
     * 获取仓库日志
     * @param repository
     * @return
     * @throws IOException
     */
    public static List<String> getLogs(Repository repository) throws IOException {
        return getLogsSinceCommit(repository, null, null);
    }

    public static List<String> getLogsSinceCommit(Repository repository, String commit) throws IOException {
        return getLogsSinceCommit(repository, null, commit);
    }

    public static List<String> getLogsSinceCommit(Repository repository, String branch, String commit) throws IOException {
        if (branch == null) {
            branch = repository.getBranch();
        }
        Ref head = repository.findRef("refs/heads/" + branch);
        List<String> commits = new ArrayList<>();
        if (head != null) {
            try (RevWalk revWalk = new RevWalk(repository)) {
                revWalk.markStart(revWalk.parseCommit(head.getObjectId()));
                for (RevCommit revCommit : revWalk) {
                    if (revCommit.getId().getName().equals(commit)) {
                        break;
                    }
                    commits.add(revCommit.getFullMessage());
                    System.out.println("\nCommit-Message: " + revCommit.getFullMessage());
                }
                revWalk.dispose();
            }
        }

        return commits;
    }

}
