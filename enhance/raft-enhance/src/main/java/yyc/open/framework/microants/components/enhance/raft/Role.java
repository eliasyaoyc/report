package yyc.open.framework.microants.components.enhance.raft;

/**
 * {@link Role}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/15
 */
public enum Role {
    Leader,
    Candidate,
    Follower,
    Visitor;
}
