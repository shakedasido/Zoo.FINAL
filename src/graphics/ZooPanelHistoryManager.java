package graphics;
import java.util.Stack;

/**
 * Manager of panel states
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class ZooPanelHistoryManager {
	private ZooPanel window;

	/**
	 * History private class allows to store states of panel
	 */
	private History history = new History();
	public ZooPanelHistoryManager(ZooPanel window) { this.window = window; }
	private class History {
		private Stack<Memento> m_undo = new Stack<>();
		public void save() {
			if(m_undo.size()<3)
				m_undo.push(window.save());
			else
				window.notifyFullHistory();
		}
		public void undo() {
			if(m_undo.size()>0)
				window.restore(m_undo.pop());
			else
				window.notifyEmptyHistory();
		}
	}

	/**
	 * Save state
	 */
	public void hitSave() { history.save(); }

	/**
	 * Load state
	 */
	public void hitUndo() { history.undo(); }
}
