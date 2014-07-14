package vise.tool;

/*   Vise is a utility class that acts as the entry point for the vise tool.  It delegates to 
 *   a ViseMechanics object and provides a set of overloads for the grip method which
 *   allows users to call grip anonymously and grip with a label on any Java type.
 *   
 *    By default, Vise uses the DefaultViseMechanics class with a file based store, however,
 *    the mechanics field is public.  Users of the tool can supply any implementation of
 *    ViseMechanics that they wish to use. 
 */

public class Vise {
	public static ViseMechanics mechanics = new DefaultViseMechanics(new FileBasedStore());
	
	public static void openSection(String section) {
		mechanics.openSection(section);
	}
	
	public static void grip(Object object) {
		mechanics.grip(object, "");
	}
	
	public static void grip(Object object, String label) {
		mechanics.grip(object, label);
	}
	
	public static void inspect() {
		mechanics.inspect();
	}
	
	public static void closeSection() {
		mechanics.closeSection();
	}
	
	public static void release() {
		mechanics.release();
	}
	
	public static void grip(byte value) {
		mechanics.grip(new Byte(value), "");
	}	
	
	public static void grip(byte value, String label) {
		mechanics.grip(new Byte(value), label);
	}	
	
	public static void grip(short value) {
		mechanics.grip(new Short(value), "");
	}
	
	public static void grip(short value, String label) {
		mechanics.grip(new Short(value), label);
	}
	
	public static void grip(int value) {
		mechanics.grip(new Integer(value), "");
	}
	
	public static void grip(int value, String label) {
		mechanics.grip(new Integer(value));
	}
	
	public static void grip(long value) {
		mechanics.grip(new Long(value), "");
	}
	
	public static void grip(long value, String label) {
		mechanics.grip(new Long(value), label);
	}
	
	public static void grip(float value) {
		mechanics.grip(new Float(value), "");
	}
	
	public static void grip(float value, String label) {
		mechanics.grip(new Float(value), label);
	}
	
	public static void grip(double value) {
		mechanics.grip(new Double(value), "");
	}	
	
	public static void grip(double value, String label) {
		mechanics.grip(new Double(value), label);
	}	
	
	public static void grip(char value) {
		mechanics.grip(new Character(value), "");
	}	
	
	public static void grip(char value, String label) {
		mechanics.grip(new Character(value), label);
	}	
	
	public static void grip(boolean value) {
		mechanics.grip(new Boolean(value), "");
	}		

	public static void grip(boolean value, String label) {
		mechanics.grip(new Boolean(value), label);
	}			
}
