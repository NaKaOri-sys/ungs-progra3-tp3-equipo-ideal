package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.NavigationController;
import equipoideal.model.Navigation;
import equipoideal.view.MainView;

public class Main {

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
		MainView mainView = new MainView();
		Navigation navigation = new Navigation();
		new NavigationController(mainView, navigation);
		
		mainView.setVisible(true);
	}

}
