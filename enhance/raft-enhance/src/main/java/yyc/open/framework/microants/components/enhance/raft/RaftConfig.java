package yyc.open.framework.microants.components.enhance.raft;

/**
 * {@link RaftConfig}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/15
 */
public class RaftConfig {
    /**
     * The minimum timeout for election, so starts election
     * if don't received heartbeat request from leader node during this time.
     */
    private long electionTimeout;
    /**
     * The interval for send heartbeat request, but its only used by leader node.
     * When follower or candidate node received heartbeat request, it will becomes a
     * follower role and reset the `electionTimeout`,
     * so we must make sure `heartbeatInterval` is far less than `electionTimeout`.
     * For example: heartbeatInterval 1s, electionTimeout 5s.
     */
    private long heartbeatInterval;
}
