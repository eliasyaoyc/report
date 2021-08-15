package yyc.open.framework.microants.components.enhance.raft.node;

import yyc.open.framework.microants.components.enhance.raft.RaftConfig;
import yyc.open.framework.microants.components.enhance.raft.event.StateChangedEvent;

/**
 * {@link Node}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/1
 */
public class Node implements StateListener {
    private RaftConfig config;
    private NodeState nodeState;

    private void becomeToLeader() {

    }

    private void becomeToCandidate() {

    }

    private void becomeToFollower() {

    }

    @Override
    public void stateChanged(StateChangedEvent event) {

    }
}
