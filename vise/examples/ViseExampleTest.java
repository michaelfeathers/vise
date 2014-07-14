package vise.examples;
import vise.tool.Vise;
import vise.tool.ViseTest;

/* This is a simple of example of Vise in action.  The first time you run the test below, it will record the value of barcode 
 * in addItem and the value of the line in the display.
 * 
 * To see how Vise works, run the test once and then comment out one of the Vise calls.  You should recieve a message 
 * which tells you that the Vise has remaining values after test execution.  Go back and uncomment the grip call.  The
 * tests should pass again.
 * 
 *   To see some of the other errors, add a new grip call and see what happens.  Try changing one of the values which
 *   is sent to grip also.
 */


class Sale
{
	class EncrustedDisplay
	{
		void showLine(String line) {
			Vise.grip(line);
			// ...
		}
	}
	
	private EncrustedDisplay display = new EncrustedDisplay();
	
	public void addItem(String barcode) {
		if (barcode.equals("1")) {
			Vise.grip(barcode);
			display.showLine("Milk $3.99");
		} else if(barcode.equals("2")) {
			display.showLine("Cornflakes $2.99");
		}
	}
}



public class ViseExampleTest extends ViseTest {
	
	public void testAddItem() {
		Sale sale =  new Sale();
		sale.addItem("1");
	}
	
	public void testAddAnotherItem() {
		Sale sale =  new Sale();
		sale.addItem("2");
	}	
}
