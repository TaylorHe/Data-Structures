//Taylor He
//I pledge my honor that I have abided by the Stevens Honor System.

public class BinaryNumber {
	/** The decimal equivalent of the BinaryNumber*/
	private int decimal;
	/** BinaryNumber represented as a String type */
	private String binNum;
	/** If the BinaryNumber is in overflow */
	private boolean overflow;

	public static void main(String[] args) {
		
		BinaryNumber bin = new BinaryNumber("10111");
		BinaryNumber bin2 = new BinaryNumber("11110");
		System.out.println(bin.toDecimal());
		System.out.println(bin2.toDecimal());
		BinaryNumber bin3 = bin.add(bin2);
		System.out.println(bin3.toString());
		System.out.println(bin3.toDecimal());
		System.out.println(bin3.isInOverflow());
		//System.out.println(bin.shiftR(2));
		System.out.println(new BinaryNumber("0").isInOverflow());
		
		bin = new BinaryNumber("10110");
		bin2 = new BinaryNumber("11100");
		System.out.println(bin.add(bin2).toString());
	}

	/** Construct a BinaryNumber with all 0's
	 *  @param length length of Binary Number
	 *  (Precondition: length > 0)
	 */
	public BinaryNumber(int length){
		assert length > 0;
		binNum = "";
		for(int i=0; i < length; i++){
			binNum += "0";
		}
	}

	/** Construct a BinaryNumber with a String
	 *  @param String String value of BinaryNumber
	 *  (Precondition: parameter str is not empty, and contains only 0s and 1s)
	 */
	public BinaryNumber(String str){
		assert str != "";
		/* ^ means start of string
		 * inside the [ ] are what you can use
		 * + means one more time
		 * $ means end of string
		 * Check that the string only contains 0's and 1's
		 */
		assert str.matches("^[01]+$");
		binNum = str;
	}
	/** Returns the length of the BinaryString
	 * @return length as integer
	 */
	public int getLength(){
		return this.binNum.length();
	}

	/** Get the digit that corresponds to the given index
	 * @param index index of BinaryNumber
	 * @return Digit as string at given index
	 * (Precondition: 0 <= index < length of BinaryNumber)
	 */
	public String getDigit(int index){
		assert index >=0;
		assert index < this.binNum.length();
		return this.binNum.toString().substring(index, index + 1);

	}
	/** Transforms a BinaryNumber into its decimal notation
	 * @return integer of the decimal equivalent
	 */
	public int toDecimal(){
		decimal = 0;
		for(int i=0; i< this.getLength(); i++){
			decimal += Math.pow(2,i)*Integer.parseInt(this.getDigit(i));
		}
		return decimal;
	}
	/** Converts a BinaryNumber to a String
	 * If it is an overflow, will return overflow.
	 *  @return String
	 */
	@Override
	public String toString(){
		return !this.overflow ? this.binNum: "Overflow";
	}
	/** Shifts all digits to the right by a given amount
	 * @param amountToShift amount of digits to shift to the right
	 * @return BinaryNumber
	 * (Precondition: argument is a non-negative number)
	 */
	public BinaryNumber shiftR(int amountToShift){
		assert amountToShift >=0;
		String addTo = "";
		for(int i = 0; i < amountToShift; i++){
			addTo += "0" ;
		}
		return new BinaryNumber(addTo + this.binNum);
	}
	/** adds two BinaryNumbers
	 * @param aBinaryNumber the BinaryNumber to be added
	 * @return a new BinaryNumber, the sum of both
	 * (Precondition: BinaryNumbers are same length)
	 */
	public BinaryNumber add(BinaryNumber aBinaryNumber){
		// Make sure same length
		assert aBinaryNumber.getLength() == this.getLength();
		
		boolean carry = false;
		String answer = "";
		for(int i = 0; i < this.getLength(); i++){
			//if this == aBinaryNumber for each digit, carry==true, then answer is 1. 
			//If false, answer is 0 and carry depends on previous carry.
			if(toBool(aBinaryNumber.getDigit(i)) ^ toBool(this.getDigit(i))){
				if(carry==false) answer = answer + "1";
				if(carry==true) answer = answer + "0";

			} //must be same number now, so check only 1 case
			// change carry as needed
			else if(this.getDigit(i).equals("0") && carry){
				answer = answer + "1";
				carry = !carry;
			} else if(this.getDigit(i).equals("0") && !carry){
				answer = answer + "0";
			} else if(this.getDigit(i).equals("1") && carry){
				answer = answer + "1";
			} else if(this.getDigit(i).equals("1") && !carry){
				answer = answer + "0";
				carry = !carry;
			} 
			//System.out.println(answer);
		}
		//If there's a leftover carry, add one to end. Make sure it is marked as overflow.
		if(carry) {
			answer = answer + "1";
			this.overflow = true;
		}
		this.binNum = answer;
		return this;
	}

	//toBool returns true if argument is "1", and false otherwise.
	//Helper function to add(BinaryNumber)
	public static boolean toBool(String s){
		return s.equals("1")? true: false;
	}
	
	/** Checks if a BinaryNumber is in overflow
	 * @return boolean
	 */
	public boolean isInOverflow(){
		return this.overflow;
	}

}
