package yyc.open.framework.microants.components.enhance.raft.node;

import yyc.open.framework.microants.components.enhance.raft.event.StateChangedEvent;

/**
 * {@link StateListener} A listener to be notified when the node state changes.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/15
 */
public interface StateListener {

    /**
     * Called when node state changes.
     */
    void stateChanged(StateChangedEvent event);
}
