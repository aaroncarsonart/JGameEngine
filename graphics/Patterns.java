package graphics;

/**
 * Contains int values from 0 - 511, derived from size 9 byte arrays.
 * @author Aaron Carson
 * @version Jul 21, 2015
 */
public class Patterns
{	
	/**
	 * Hidden constructor.
	 */
	private Patterns(){}
	
	/**
	 * Convert a byte pattern to an integer representation.
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getStringFromPattern(byte[] pattern, int cellType) {
		int length = pattern.length;
		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++) {
				sb.append(pattern[i] == cellType ? '1':'0');
		}
		return sb.toString();
	}
	
	/**
	 * Get the base 10 integer representation of a binary string.
	 * 
	 * @param pattern The binary (0's and 1's) string.
	 * @return The integer form of the binary string.
	 */
	public static int getIntFromBinaryString(String pattern) {
		return Integer.parseInt(pattern, 2);
	}
	
	// ************************************************************************
	// CONSTANTS
	// ************************************************************************
	
	/**
	000000000
	<pre><code>
	0,0,0,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_0 = 0;

	/**
	000000001
	<pre><code>
	0,0,0,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_1 = 1;

	/**
	000000010
	<pre><code>
	0,0,0,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_2 = 2;

	/**
	000000011
	<pre><code>
	0,0,0,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_3 = 3;

	/**
	000000100
	<pre><code>
	0,0,0,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_4 = 4;

	/**
	000000101
	<pre><code>
	0,0,0,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_5 = 5;

	/**
	000000110
	<pre><code>
	0,0,0,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_6 = 6;

	/**
	000000111
	<pre><code>
	0,0,0,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_7 = 7;

	/**
	000001000
	<pre><code>
	0,0,0,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_8 = 8;

	/**
	000001001
	<pre><code>
	0,0,0,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_9 = 9;

	/**
	000001010
	<pre><code>
	0,0,0,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_10 = 10;

	/**
	000001011
	<pre><code>
	0,0,0,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_11 = 11;

	/**
	000001100
	<pre><code>
	0,0,0,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_12 = 12;

	/**
	000001101
	<pre><code>
	0,0,0,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_13 = 13;

	/**
	000001110
	<pre><code>
	0,0,0,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_14 = 14;

	/**
	000001111
	<pre><code>
	0,0,0,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_15 = 15;

	/**
	000010000
	<pre><code>
	0,0,0,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_16 = 16;

	/**
	000010001
	<pre><code>
	0,0,0,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_17 = 17;

	/**
	000010010
	<pre><code>
	0,0,0,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_18 = 18;

	/**
	000010011
	<pre><code>
	0,0,0,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_19 = 19;

	/**
	000010100
	<pre><code>
	0,0,0,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_20 = 20;

	/**
	000010101
	<pre><code>
	0,0,0,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_21 = 21;

	/**
	000010110
	<pre><code>
	0,0,0,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_22 = 22;

	/**
	000010111
	<pre><code>
	0,0,0,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_23 = 23;

	/**
	000011000
	<pre><code>
	0,0,0,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_24 = 24;

	/**
	000011001
	<pre><code>
	0,0,0,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_25 = 25;

	/**
	000011010
	<pre><code>
	0,0,0,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_26 = 26;

	/**
	000011011
	<pre><code>
	0,0,0,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_27 = 27;

	/**
	000011100
	<pre><code>
	0,0,0,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_28 = 28;

	/**
	000011101
	<pre><code>
	0,0,0,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_29 = 29;

	/**
	000011110
	<pre><code>
	0,0,0,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_30 = 30;

	/**
	000011111
	<pre><code>
	0,0,0,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_31 = 31;

	/**
	000100000
	<pre><code>
	0,0,0,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_32 = 32;

	/**
	000100001
	<pre><code>
	0,0,0,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_33 = 33;

	/**
	000100010
	<pre><code>
	0,0,0,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_34 = 34;

	/**
	000100011
	<pre><code>
	0,0,0,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_35 = 35;

	/**
	000100100
	<pre><code>
	0,0,0,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_36 = 36;

	/**
	000100101
	<pre><code>
	0,0,0,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_37 = 37;

	/**
	000100110
	<pre><code>
	0,0,0,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_38 = 38;

	/**
	000100111
	<pre><code>
	0,0,0,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_39 = 39;

	/**
	000101000
	<pre><code>
	0,0,0,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_40 = 40;

	/**
	000101001
	<pre><code>
	0,0,0,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_41 = 41;

	/**
	000101010
	<pre><code>
	0,0,0,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_42 = 42;

	/**
	000101011
	<pre><code>
	0,0,0,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_43 = 43;

	/**
	000101100
	<pre><code>
	0,0,0,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_44 = 44;

	/**
	000101101
	<pre><code>
	0,0,0,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_45 = 45;

	/**
	000101110
	<pre><code>
	0,0,0,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_46 = 46;

	/**
	000101111
	<pre><code>
	0,0,0,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_47 = 47;

	/**
	000110000
	<pre><code>
	0,0,0,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_48 = 48;

	/**
	000110001
	<pre><code>
	0,0,0,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_49 = 49;

	/**
	000110010
	<pre><code>
	0,0,0,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_50 = 50;

	/**
	000110011
	<pre><code>
	0,0,0,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_51 = 51;

	/**
	000110100
	<pre><code>
	0,0,0,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_52 = 52;

	/**
	000110101
	<pre><code>
	0,0,0,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_53 = 53;

	/**
	000110110
	<pre><code>
	0,0,0,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_54 = 54;

	/**
	000110111
	<pre><code>
	0,0,0,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_55 = 55;

	/**
	000111000
	<pre><code>
	0,0,0,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_56 = 56;

	/**
	000111001
	<pre><code>
	0,0,0,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_57 = 57;

	/**
	000111010
	<pre><code>
	0,0,0,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_58 = 58;

	/**
	000111011
	<pre><code>
	0,0,0,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_59 = 59;

	/**
	000111100
	<pre><code>
	0,0,0,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_60 = 60;

	/**
	000111101
	<pre><code>
	0,0,0,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_61 = 61;

	/**
	000111110
	<pre><code>
	0,0,0,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_62 = 62;

	/**
	000111111
	<pre><code>
	0,0,0,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_63 = 63;

	/**
	001000000
	<pre><code>
	0,0,1,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_64 = 64;

	/**
	001000001
	<pre><code>
	0,0,1,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_65 = 65;

	/**
	001000010
	<pre><code>
	0,0,1,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_66 = 66;

	/**
	001000011
	<pre><code>
	0,0,1,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_67 = 67;

	/**
	001000100
	<pre><code>
	0,0,1,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_68 = 68;

	/**
	001000101
	<pre><code>
	0,0,1,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_69 = 69;

	/**
	001000110
	<pre><code>
	0,0,1,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_70 = 70;

	/**
	001000111
	<pre><code>
	0,0,1,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_71 = 71;

	/**
	001001000
	<pre><code>
	0,0,1,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_72 = 72;

	/**
	001001001
	<pre><code>
	0,0,1,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_73 = 73;

	/**
	001001010
	<pre><code>
	0,0,1,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_74 = 74;

	/**
	001001011
	<pre><code>
	0,0,1,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_75 = 75;

	/**
	001001100
	<pre><code>
	0,0,1,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_76 = 76;

	/**
	001001101
	<pre><code>
	0,0,1,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_77 = 77;

	/**
	001001110
	<pre><code>
	0,0,1,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_78 = 78;

	/**
	001001111
	<pre><code>
	0,0,1,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_79 = 79;

	/**
	001010000
	<pre><code>
	0,0,1,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_80 = 80;

	/**
	001010001
	<pre><code>
	0,0,1,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_81 = 81;

	/**
	001010010
	<pre><code>
	0,0,1,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_82 = 82;

	/**
	001010011
	<pre><code>
	0,0,1,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_83 = 83;

	/**
	001010100
	<pre><code>
	0,0,1,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_84 = 84;

	/**
	001010101
	<pre><code>
	0,0,1,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_85 = 85;

	/**
	001010110
	<pre><code>
	0,0,1,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_86 = 86;

	/**
	001010111
	<pre><code>
	0,0,1,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_87 = 87;

	/**
	001011000
	<pre><code>
	0,0,1,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_88 = 88;

	/**
	001011001
	<pre><code>
	0,0,1,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_89 = 89;

	/**
	001011010
	<pre><code>
	0,0,1,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_90 = 90;

	/**
	001011011
	<pre><code>
	0,0,1,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_91 = 91;

	/**
	001011100
	<pre><code>
	0,0,1,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_92 = 92;

	/**
	001011101
	<pre><code>
	0,0,1,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_93 = 93;

	/**
	001011110
	<pre><code>
	0,0,1,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_94 = 94;

	/**
	001011111
	<pre><code>
	0,0,1,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_95 = 95;

	/**
	001100000
	<pre><code>
	0,0,1,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_96 = 96;

	/**
	001100001
	<pre><code>
	0,0,1,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_97 = 97;

	/**
	001100010
	<pre><code>
	0,0,1,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_98 = 98;

	/**
	001100011
	<pre><code>
	0,0,1,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_99 = 99;

	/**
	001100100
	<pre><code>
	0,0,1,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_100 = 100;

	/**
	001100101
	<pre><code>
	0,0,1,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_101 = 101;

	/**
	001100110
	<pre><code>
	0,0,1,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_102 = 102;

	/**
	001100111
	<pre><code>
	0,0,1,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_103 = 103;

	/**
	001101000
	<pre><code>
	0,0,1,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_104 = 104;

	/**
	001101001
	<pre><code>
	0,0,1,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_105 = 105;

	/**
	001101010
	<pre><code>
	0,0,1,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_106 = 106;

	/**
	001101011
	<pre><code>
	0,0,1,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_107 = 107;

	/**
	001101100
	<pre><code>
	0,0,1,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_108 = 108;

	/**
	001101101
	<pre><code>
	0,0,1,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_109 = 109;

	/**
	001101110
	<pre><code>
	0,0,1,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_110 = 110;

	/**
	001101111
	<pre><code>
	0,0,1,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_111 = 111;

	/**
	001110000
	<pre><code>
	0,0,1,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_112 = 112;

	/**
	001110001
	<pre><code>
	0,0,1,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_113 = 113;

	/**
	001110010
	<pre><code>
	0,0,1,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_114 = 114;

	/**
	001110011
	<pre><code>
	0,0,1,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_115 = 115;

	/**
	001110100
	<pre><code>
	0,0,1,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_116 = 116;

	/**
	001110101
	<pre><code>
	0,0,1,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_117 = 117;

	/**
	001110110
	<pre><code>
	0,0,1,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_118 = 118;

	/**
	001110111
	<pre><code>
	0,0,1,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_119 = 119;

	/**
	001111000
	<pre><code>
	0,0,1,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_120 = 120;

	/**
	001111001
	<pre><code>
	0,0,1,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_121 = 121;

	/**
	001111010
	<pre><code>
	0,0,1,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_122 = 122;

	/**
	001111011
	<pre><code>
	0,0,1,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_123 = 123;

	/**
	001111100
	<pre><code>
	0,0,1,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_124 = 124;

	/**
	001111101
	<pre><code>
	0,0,1,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_125 = 125;

	/**
	001111110
	<pre><code>
	0,0,1,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_126 = 126;

	/**
	001111111
	<pre><code>
	0,0,1,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_127 = 127;

	/**
	010000000
	<pre><code>
	0,1,0,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_128 = 128;

	/**
	010000001
	<pre><code>
	0,1,0,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_129 = 129;

	/**
	010000010
	<pre><code>
	0,1,0,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_130 = 130;

	/**
	010000011
	<pre><code>
	0,1,0,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_131 = 131;

	/**
	010000100
	<pre><code>
	0,1,0,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_132 = 132;

	/**
	010000101
	<pre><code>
	0,1,0,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_133 = 133;

	/**
	010000110
	<pre><code>
	0,1,0,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_134 = 134;

	/**
	010000111
	<pre><code>
	0,1,0,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_135 = 135;

	/**
	010001000
	<pre><code>
	0,1,0,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_136 = 136;

	/**
	010001001
	<pre><code>
	0,1,0,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_137 = 137;

	/**
	010001010
	<pre><code>
	0,1,0,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_138 = 138;

	/**
	010001011
	<pre><code>
	0,1,0,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_139 = 139;

	/**
	010001100
	<pre><code>
	0,1,0,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_140 = 140;

	/**
	010001101
	<pre><code>
	0,1,0,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_141 = 141;

	/**
	010001110
	<pre><code>
	0,1,0,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_142 = 142;

	/**
	010001111
	<pre><code>
	0,1,0,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_143 = 143;

	/**
	010010000
	<pre><code>
	0,1,0,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_144 = 144;

	/**
	010010001
	<pre><code>
	0,1,0,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_145 = 145;

	/**
	010010010
	<pre><code>
	0,1,0,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_146 = 146;

	/**
	010010011
	<pre><code>
	0,1,0,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_147 = 147;

	/**
	010010100
	<pre><code>
	0,1,0,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_148 = 148;

	/**
	010010101
	<pre><code>
	0,1,0,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_149 = 149;

	/**
	010010110
	<pre><code>
	0,1,0,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_150 = 150;

	/**
	010010111
	<pre><code>
	0,1,0,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_151 = 151;

	/**
	010011000
	<pre><code>
	0,1,0,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_152 = 152;

	/**
	010011001
	<pre><code>
	0,1,0,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_153 = 153;

	/**
	010011010
	<pre><code>
	0,1,0,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_154 = 154;

	/**
	010011011
	<pre><code>
	0,1,0,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_155 = 155;

	/**
	010011100
	<pre><code>
	0,1,0,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_156 = 156;

	/**
	010011101
	<pre><code>
	0,1,0,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_157 = 157;

	/**
	010011110
	<pre><code>
	0,1,0,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_158 = 158;

	/**
	010011111
	<pre><code>
	0,1,0,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_159 = 159;

	/**
	010100000
	<pre><code>
	0,1,0,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_160 = 160;

	/**
	010100001
	<pre><code>
	0,1,0,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_161 = 161;

	/**
	010100010
	<pre><code>
	0,1,0,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_162 = 162;

	/**
	010100011
	<pre><code>
	0,1,0,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_163 = 163;

	/**
	010100100
	<pre><code>
	0,1,0,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_164 = 164;

	/**
	010100101
	<pre><code>
	0,1,0,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_165 = 165;

	/**
	010100110
	<pre><code>
	0,1,0,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_166 = 166;

	/**
	010100111
	<pre><code>
	0,1,0,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_167 = 167;

	/**
	010101000
	<pre><code>
	0,1,0,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_168 = 168;

	/**
	010101001
	<pre><code>
	0,1,0,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_169 = 169;

	/**
	010101010
	<pre><code>
	0,1,0,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_170 = 170;

	/**
	010101011
	<pre><code>
	0,1,0,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_171 = 171;

	/**
	010101100
	<pre><code>
	0,1,0,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_172 = 172;

	/**
	010101101
	<pre><code>
	0,1,0,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_173 = 173;

	/**
	010101110
	<pre><code>
	0,1,0,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_174 = 174;

	/**
	010101111
	<pre><code>
	0,1,0,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_175 = 175;

	/**
	010110000
	<pre><code>
	0,1,0,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_176 = 176;

	/**
	010110001
	<pre><code>
	0,1,0,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_177 = 177;

	/**
	010110010
	<pre><code>
	0,1,0,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_178 = 178;

	/**
	010110011
	<pre><code>
	0,1,0,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_179 = 179;

	/**
	010110100
	<pre><code>
	0,1,0,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_180 = 180;

	/**
	010110101
	<pre><code>
	0,1,0,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_181 = 181;

	/**
	010110110
	<pre><code>
	0,1,0,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_182 = 182;

	/**
	010110111
	<pre><code>
	0,1,0,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_183 = 183;

	/**
	010111000
	<pre><code>
	0,1,0,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_184 = 184;

	/**
	010111001
	<pre><code>
	0,1,0,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_185 = 185;

	/**
	010111010
	<pre><code>
	0,1,0,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_186 = 186;

	/**
	010111011
	<pre><code>
	0,1,0,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_187 = 187;

	/**
	010111100
	<pre><code>
	0,1,0,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_188 = 188;

	/**
	010111101
	<pre><code>
	0,1,0,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_189 = 189;

	/**
	010111110
	<pre><code>
	0,1,0,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_190 = 190;

	/**
	010111111
	<pre><code>
	0,1,0,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_191 = 191;

	/**
	011000000
	<pre><code>
	0,1,1,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_192 = 192;

	/**
	011000001
	<pre><code>
	0,1,1,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_193 = 193;

	/**
	011000010
	<pre><code>
	0,1,1,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_194 = 194;

	/**
	011000011
	<pre><code>
	0,1,1,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_195 = 195;

	/**
	011000100
	<pre><code>
	0,1,1,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_196 = 196;

	/**
	011000101
	<pre><code>
	0,1,1,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_197 = 197;

	/**
	011000110
	<pre><code>
	0,1,1,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_198 = 198;

	/**
	011000111
	<pre><code>
	0,1,1,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_199 = 199;

	/**
	011001000
	<pre><code>
	0,1,1,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_200 = 200;

	/**
	011001001
	<pre><code>
	0,1,1,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_201 = 201;

	/**
	011001010
	<pre><code>
	0,1,1,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_202 = 202;

	/**
	011001011
	<pre><code>
	0,1,1,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_203 = 203;

	/**
	011001100
	<pre><code>
	0,1,1,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_204 = 204;

	/**
	011001101
	<pre><code>
	0,1,1,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_205 = 205;

	/**
	011001110
	<pre><code>
	0,1,1,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_206 = 206;

	/**
	011001111
	<pre><code>
	0,1,1,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_207 = 207;

	/**
	011010000
	<pre><code>
	0,1,1,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_208 = 208;

	/**
	011010001
	<pre><code>
	0,1,1,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_209 = 209;

	/**
	011010010
	<pre><code>
	0,1,1,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_210 = 210;

	/**
	011010011
	<pre><code>
	0,1,1,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_211 = 211;

	/**
	011010100
	<pre><code>
	0,1,1,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_212 = 212;

	/**
	011010101
	<pre><code>
	0,1,1,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_213 = 213;

	/**
	011010110
	<pre><code>
	0,1,1,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_214 = 214;

	/**
	011010111
	<pre><code>
	0,1,1,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_215 = 215;

	/**
	011011000
	<pre><code>
	0,1,1,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_216 = 216;

	/**
	011011001
	<pre><code>
	0,1,1,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_217 = 217;

	/**
	011011010
	<pre><code>
	0,1,1,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_218 = 218;

	/**
	011011011
	<pre><code>
	0,1,1,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_219 = 219;

	/**
	011011100
	<pre><code>
	0,1,1,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_220 = 220;

	/**
	011011101
	<pre><code>
	0,1,1,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_221 = 221;

	/**
	011011110
	<pre><code>
	0,1,1,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_222 = 222;

	/**
	011011111
	<pre><code>
	0,1,1,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_223 = 223;

	/**
	011100000
	<pre><code>
	0,1,1,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_224 = 224;

	/**
	011100001
	<pre><code>
	0,1,1,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_225 = 225;

	/**
	011100010
	<pre><code>
	0,1,1,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_226 = 226;

	/**
	011100011
	<pre><code>
	0,1,1,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_227 = 227;

	/**
	011100100
	<pre><code>
	0,1,1,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_228 = 228;

	/**
	011100101
	<pre><code>
	0,1,1,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_229 = 229;

	/**
	011100110
	<pre><code>
	0,1,1,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_230 = 230;

	/**
	011100111
	<pre><code>
	0,1,1,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_231 = 231;

	/**
	011101000
	<pre><code>
	0,1,1,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_232 = 232;

	/**
	011101001
	<pre><code>
	0,1,1,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_233 = 233;

	/**
	011101010
	<pre><code>
	0,1,1,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_234 = 234;

	/**
	011101011
	<pre><code>
	0,1,1,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_235 = 235;

	/**
	011101100
	<pre><code>
	0,1,1,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_236 = 236;

	/**
	011101101
	<pre><code>
	0,1,1,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_237 = 237;

	/**
	011101110
	<pre><code>
	0,1,1,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_238 = 238;

	/**
	011101111
	<pre><code>
	0,1,1,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_239 = 239;

	/**
	011110000
	<pre><code>
	0,1,1,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_240 = 240;

	/**
	011110001
	<pre><code>
	0,1,1,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_241 = 241;

	/**
	011110010
	<pre><code>
	0,1,1,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_242 = 242;

	/**
	011110011
	<pre><code>
	0,1,1,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_243 = 243;

	/**
	011110100
	<pre><code>
	0,1,1,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_244 = 244;

	/**
	011110101
	<pre><code>
	0,1,1,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_245 = 245;

	/**
	011110110
	<pre><code>
	0,1,1,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_246 = 246;

	/**
	011110111
	<pre><code>
	0,1,1,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_247 = 247;

	/**
	011111000
	<pre><code>
	0,1,1,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_248 = 248;

	/**
	011111001
	<pre><code>
	0,1,1,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_249 = 249;

	/**
	011111010
	<pre><code>
	0,1,1,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_250 = 250;

	/**
	011111011
	<pre><code>
	0,1,1,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_251 = 251;

	/**
	011111100
	<pre><code>
	0,1,1,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_252 = 252;

	/**
	011111101
	<pre><code>
	0,1,1,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_253 = 253;

	/**
	011111110
	<pre><code>
	0,1,1,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_254 = 254;

	/**
	011111111
	<pre><code>
	0,1,1,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_255 = 255;

	/**
	100000000
	<pre><code>
	1,0,0,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_256 = 256;

	/**
	100000001
	<pre><code>
	1,0,0,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_257 = 257;

	/**
	100000010
	<pre><code>
	1,0,0,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_258 = 258;

	/**
	100000011
	<pre><code>
	1,0,0,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_259 = 259;

	/**
	100000100
	<pre><code>
	1,0,0,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_260 = 260;

	/**
	100000101
	<pre><code>
	1,0,0,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_261 = 261;

	/**
	100000110
	<pre><code>
	1,0,0,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_262 = 262;

	/**
	100000111
	<pre><code>
	1,0,0,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_263 = 263;

	/**
	100001000
	<pre><code>
	1,0,0,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_264 = 264;

	/**
	100001001
	<pre><code>
	1,0,0,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_265 = 265;

	/**
	100001010
	<pre><code>
	1,0,0,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_266 = 266;

	/**
	100001011
	<pre><code>
	1,0,0,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_267 = 267;

	/**
	100001100
	<pre><code>
	1,0,0,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_268 = 268;

	/**
	100001101
	<pre><code>
	1,0,0,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_269 = 269;

	/**
	100001110
	<pre><code>
	1,0,0,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_270 = 270;

	/**
	100001111
	<pre><code>
	1,0,0,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_271 = 271;

	/**
	100010000
	<pre><code>
	1,0,0,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_272 = 272;

	/**
	100010001
	<pre><code>
	1,0,0,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_273 = 273;

	/**
	100010010
	<pre><code>
	1,0,0,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_274 = 274;

	/**
	100010011
	<pre><code>
	1,0,0,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_275 = 275;

	/**
	100010100
	<pre><code>
	1,0,0,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_276 = 276;

	/**
	100010101
	<pre><code>
	1,0,0,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_277 = 277;

	/**
	100010110
	<pre><code>
	1,0,0,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_278 = 278;

	/**
	100010111
	<pre><code>
	1,0,0,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_279 = 279;

	/**
	100011000
	<pre><code>
	1,0,0,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_280 = 280;

	/**
	100011001
	<pre><code>
	1,0,0,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_281 = 281;

	/**
	100011010
	<pre><code>
	1,0,0,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_282 = 282;

	/**
	100011011
	<pre><code>
	1,0,0,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_283 = 283;

	/**
	100011100
	<pre><code>
	1,0,0,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_284 = 284;

	/**
	100011101
	<pre><code>
	1,0,0,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_285 = 285;

	/**
	100011110
	<pre><code>
	1,0,0,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_286 = 286;

	/**
	100011111
	<pre><code>
	1,0,0,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_287 = 287;

	/**
	100100000
	<pre><code>
	1,0,0,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_288 = 288;

	/**
	100100001
	<pre><code>
	1,0,0,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_289 = 289;

	/**
	100100010
	<pre><code>
	1,0,0,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_290 = 290;

	/**
	100100011
	<pre><code>
	1,0,0,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_291 = 291;

	/**
	100100100
	<pre><code>
	1,0,0,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_292 = 292;

	/**
	100100101
	<pre><code>
	1,0,0,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_293 = 293;

	/**
	100100110
	<pre><code>
	1,0,0,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_294 = 294;

	/**
	100100111
	<pre><code>
	1,0,0,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_295 = 295;

	/**
	100101000
	<pre><code>
	1,0,0,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_296 = 296;

	/**
	100101001
	<pre><code>
	1,0,0,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_297 = 297;

	/**
	100101010
	<pre><code>
	1,0,0,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_298 = 298;

	/**
	100101011
	<pre><code>
	1,0,0,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_299 = 299;

	/**
	100101100
	<pre><code>
	1,0,0,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_300 = 300;

	/**
	100101101
	<pre><code>
	1,0,0,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_301 = 301;

	/**
	100101110
	<pre><code>
	1,0,0,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_302 = 302;

	/**
	100101111
	<pre><code>
	1,0,0,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_303 = 303;

	/**
	100110000
	<pre><code>
	1,0,0,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_304 = 304;

	/**
	100110001
	<pre><code>
	1,0,0,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_305 = 305;

	/**
	100110010
	<pre><code>
	1,0,0,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_306 = 306;

	/**
	100110011
	<pre><code>
	1,0,0,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_307 = 307;

	/**
	100110100
	<pre><code>
	1,0,0,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_308 = 308;

	/**
	100110101
	<pre><code>
	1,0,0,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_309 = 309;

	/**
	100110110
	<pre><code>
	1,0,0,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_310 = 310;

	/**
	100110111
	<pre><code>
	1,0,0,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_311 = 311;

	/**
	100111000
	<pre><code>
	1,0,0,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_312 = 312;

	/**
	100111001
	<pre><code>
	1,0,0,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_313 = 313;

	/**
	100111010
	<pre><code>
	1,0,0,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_314 = 314;

	/**
	100111011
	<pre><code>
	1,0,0,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_315 = 315;

	/**
	100111100
	<pre><code>
	1,0,0,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_316 = 316;

	/**
	100111101
	<pre><code>
	1,0,0,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_317 = 317;

	/**
	100111110
	<pre><code>
	1,0,0,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_318 = 318;

	/**
	100111111
	<pre><code>
	1,0,0,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_319 = 319;

	/**
	101000000
	<pre><code>
	1,0,1,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_320 = 320;

	/**
	101000001
	<pre><code>
	1,0,1,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_321 = 321;

	/**
	101000010
	<pre><code>
	1,0,1,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_322 = 322;

	/**
	101000011
	<pre><code>
	1,0,1,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_323 = 323;

	/**
	101000100
	<pre><code>
	1,0,1,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_324 = 324;

	/**
	101000101
	<pre><code>
	1,0,1,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_325 = 325;

	/**
	101000110
	<pre><code>
	1,0,1,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_326 = 326;

	/**
	101000111
	<pre><code>
	1,0,1,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_327 = 327;

	/**
	101001000
	<pre><code>
	1,0,1,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_328 = 328;

	/**
	101001001
	<pre><code>
	1,0,1,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_329 = 329;

	/**
	101001010
	<pre><code>
	1,0,1,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_330 = 330;

	/**
	101001011
	<pre><code>
	1,0,1,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_331 = 331;

	/**
	101001100
	<pre><code>
	1,0,1,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_332 = 332;

	/**
	101001101
	<pre><code>
	1,0,1,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_333 = 333;

	/**
	101001110
	<pre><code>
	1,0,1,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_334 = 334;

	/**
	101001111
	<pre><code>
	1,0,1,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_335 = 335;

	/**
	101010000
	<pre><code>
	1,0,1,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_336 = 336;

	/**
	101010001
	<pre><code>
	1,0,1,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_337 = 337;

	/**
	101010010
	<pre><code>
	1,0,1,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_338 = 338;

	/**
	101010011
	<pre><code>
	1,0,1,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_339 = 339;

	/**
	101010100
	<pre><code>
	1,0,1,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_340 = 340;

	/**
	101010101
	<pre><code>
	1,0,1,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_341 = 341;

	/**
	101010110
	<pre><code>
	1,0,1,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_342 = 342;

	/**
	101010111
	<pre><code>
	1,0,1,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_343 = 343;

	/**
	101011000
	<pre><code>
	1,0,1,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_344 = 344;

	/**
	101011001
	<pre><code>
	1,0,1,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_345 = 345;

	/**
	101011010
	<pre><code>
	1,0,1,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_346 = 346;

	/**
	101011011
	<pre><code>
	1,0,1,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_347 = 347;

	/**
	101011100
	<pre><code>
	1,0,1,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_348 = 348;

	/**
	101011101
	<pre><code>
	1,0,1,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_349 = 349;

	/**
	101011110
	<pre><code>
	1,0,1,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_350 = 350;

	/**
	101011111
	<pre><code>
	1,0,1,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_351 = 351;

	/**
	101100000
	<pre><code>
	1,0,1,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_352 = 352;

	/**
	101100001
	<pre><code>
	1,0,1,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_353 = 353;

	/**
	101100010
	<pre><code>
	1,0,1,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_354 = 354;

	/**
	101100011
	<pre><code>
	1,0,1,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_355 = 355;

	/**
	101100100
	<pre><code>
	1,0,1,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_356 = 356;

	/**
	101100101
	<pre><code>
	1,0,1,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_357 = 357;

	/**
	101100110
	<pre><code>
	1,0,1,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_358 = 358;

	/**
	101100111
	<pre><code>
	1,0,1,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_359 = 359;

	/**
	101101000
	<pre><code>
	1,0,1,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_360 = 360;

	/**
	101101001
	<pre><code>
	1,0,1,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_361 = 361;

	/**
	101101010
	<pre><code>
	1,0,1,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_362 = 362;

	/**
	101101011
	<pre><code>
	1,0,1,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_363 = 363;

	/**
	101101100
	<pre><code>
	1,0,1,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_364 = 364;

	/**
	101101101
	<pre><code>
	1,0,1,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_365 = 365;

	/**
	101101110
	<pre><code>
	1,0,1,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_366 = 366;

	/**
	101101111
	<pre><code>
	1,0,1,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_367 = 367;

	/**
	101110000
	<pre><code>
	1,0,1,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_368 = 368;

	/**
	101110001
	<pre><code>
	1,0,1,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_369 = 369;

	/**
	101110010
	<pre><code>
	1,0,1,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_370 = 370;

	/**
	101110011
	<pre><code>
	1,0,1,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_371 = 371;

	/**
	101110100
	<pre><code>
	1,0,1,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_372 = 372;

	/**
	101110101
	<pre><code>
	1,0,1,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_373 = 373;

	/**
	101110110
	<pre><code>
	1,0,1,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_374 = 374;

	/**
	101110111
	<pre><code>
	1,0,1,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_375 = 375;

	/**
	101111000
	<pre><code>
	1,0,1,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_376 = 376;

	/**
	101111001
	<pre><code>
	1,0,1,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_377 = 377;

	/**
	101111010
	<pre><code>
	1,0,1,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_378 = 378;

	/**
	101111011
	<pre><code>
	1,0,1,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_379 = 379;

	/**
	101111100
	<pre><code>
	1,0,1,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_380 = 380;

	/**
	101111101
	<pre><code>
	1,0,1,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_381 = 381;

	/**
	101111110
	<pre><code>
	1,0,1,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_382 = 382;

	/**
	101111111
	<pre><code>
	1,0,1,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_383 = 383;

	/**
	110000000
	<pre><code>
	1,1,0,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_384 = 384;

	/**
	110000001
	<pre><code>
	1,1,0,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_385 = 385;

	/**
	110000010
	<pre><code>
	1,1,0,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_386 = 386;

	/**
	110000011
	<pre><code>
	1,1,0,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_387 = 387;

	/**
	110000100
	<pre><code>
	1,1,0,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_388 = 388;

	/**
	110000101
	<pre><code>
	1,1,0,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_389 = 389;

	/**
	110000110
	<pre><code>
	1,1,0,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_390 = 390;

	/**
	110000111
	<pre><code>
	1,1,0,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_391 = 391;

	/**
	110001000
	<pre><code>
	1,1,0,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_392 = 392;

	/**
	110001001
	<pre><code>
	1,1,0,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_393 = 393;

	/**
	110001010
	<pre><code>
	1,1,0,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_394 = 394;

	/**
	110001011
	<pre><code>
	1,1,0,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_395 = 395;

	/**
	110001100
	<pre><code>
	1,1,0,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_396 = 396;

	/**
	110001101
	<pre><code>
	1,1,0,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_397 = 397;

	/**
	110001110
	<pre><code>
	1,1,0,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_398 = 398;

	/**
	110001111
	<pre><code>
	1,1,0,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_399 = 399;

	/**
	110010000
	<pre><code>
	1,1,0,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_400 = 400;

	/**
	110010001
	<pre><code>
	1,1,0,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_401 = 401;

	/**
	110010010
	<pre><code>
	1,1,0,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_402 = 402;

	/**
	110010011
	<pre><code>
	1,1,0,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_403 = 403;

	/**
	110010100
	<pre><code>
	1,1,0,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_404 = 404;

	/**
	110010101
	<pre><code>
	1,1,0,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_405 = 405;

	/**
	110010110
	<pre><code>
	1,1,0,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_406 = 406;

	/**
	110010111
	<pre><code>
	1,1,0,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_407 = 407;

	/**
	110011000
	<pre><code>
	1,1,0,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_408 = 408;

	/**
	110011001
	<pre><code>
	1,1,0,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_409 = 409;

	/**
	110011010
	<pre><code>
	1,1,0,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_410 = 410;

	/**
	110011011
	<pre><code>
	1,1,0,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_411 = 411;

	/**
	110011100
	<pre><code>
	1,1,0,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_412 = 412;

	/**
	110011101
	<pre><code>
	1,1,0,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_413 = 413;

	/**
	110011110
	<pre><code>
	1,1,0,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_414 = 414;

	/**
	110011111
	<pre><code>
	1,1,0,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_415 = 415;

	/**
	110100000
	<pre><code>
	1,1,0,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_416 = 416;

	/**
	110100001
	<pre><code>
	1,1,0,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_417 = 417;

	/**
	110100010
	<pre><code>
	1,1,0,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_418 = 418;

	/**
	110100011
	<pre><code>
	1,1,0,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_419 = 419;

	/**
	110100100
	<pre><code>
	1,1,0,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_420 = 420;

	/**
	110100101
	<pre><code>
	1,1,0,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_421 = 421;

	/**
	110100110
	<pre><code>
	1,1,0,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_422 = 422;

	/**
	110100111
	<pre><code>
	1,1,0,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_423 = 423;

	/**
	110101000
	<pre><code>
	1,1,0,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_424 = 424;

	/**
	110101001
	<pre><code>
	1,1,0,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_425 = 425;

	/**
	110101010
	<pre><code>
	1,1,0,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_426 = 426;

	/**
	110101011
	<pre><code>
	1,1,0,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_427 = 427;

	/**
	110101100
	<pre><code>
	1,1,0,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_428 = 428;

	/**
	110101101
	<pre><code>
	1,1,0,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_429 = 429;

	/**
	110101110
	<pre><code>
	1,1,0,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_430 = 430;

	/**
	110101111
	<pre><code>
	1,1,0,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_431 = 431;

	/**
	110110000
	<pre><code>
	1,1,0,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_432 = 432;

	/**
	110110001
	<pre><code>
	1,1,0,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_433 = 433;

	/**
	110110010
	<pre><code>
	1,1,0,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_434 = 434;

	/**
	110110011
	<pre><code>
	1,1,0,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_435 = 435;

	/**
	110110100
	<pre><code>
	1,1,0,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_436 = 436;

	/**
	110110101
	<pre><code>
	1,1,0,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_437 = 437;

	/**
	110110110
	<pre><code>
	1,1,0,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_438 = 438;

	/**
	110110111
	<pre><code>
	1,1,0,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_439 = 439;

	/**
	110111000
	<pre><code>
	1,1,0,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_440 = 440;

	/**
	110111001
	<pre><code>
	1,1,0,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_441 = 441;

	/**
	110111010
	<pre><code>
	1,1,0,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_442 = 442;

	/**
	110111011
	<pre><code>
	1,1,0,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_443 = 443;

	/**
	110111100
	<pre><code>
	1,1,0,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_444 = 444;

	/**
	110111101
	<pre><code>
	1,1,0,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_445 = 445;

	/**
	110111110
	<pre><code>
	1,1,0,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_446 = 446;

	/**
	110111111
	<pre><code>
	1,1,0,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_447 = 447;

	/**
	111000000
	<pre><code>
	1,1,1,
	0,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_448 = 448;

	/**
	111000001
	<pre><code>
	1,1,1,
	0,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_449 = 449;

	/**
	111000010
	<pre><code>
	1,1,1,
	0,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_450 = 450;

	/**
	111000011
	<pre><code>
	1,1,1,
	0,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_451 = 451;

	/**
	111000100
	<pre><code>
	1,1,1,
	0,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_452 = 452;

	/**
	111000101
	<pre><code>
	1,1,1,
	0,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_453 = 453;

	/**
	111000110
	<pre><code>
	1,1,1,
	0,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_454 = 454;

	/**
	111000111
	<pre><code>
	1,1,1,
	0,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_455 = 455;

	/**
	111001000
	<pre><code>
	1,1,1,
	0,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_456 = 456;

	/**
	111001001
	<pre><code>
	1,1,1,
	0,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_457 = 457;

	/**
	111001010
	<pre><code>
	1,1,1,
	0,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_458 = 458;

	/**
	111001011
	<pre><code>
	1,1,1,
	0,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_459 = 459;

	/**
	111001100
	<pre><code>
	1,1,1,
	0,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_460 = 460;

	/**
	111001101
	<pre><code>
	1,1,1,
	0,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_461 = 461;

	/**
	111001110
	<pre><code>
	1,1,1,
	0,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_462 = 462;

	/**
	111001111
	<pre><code>
	1,1,1,
	0,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_463 = 463;

	/**
	111010000
	<pre><code>
	1,1,1,
	0,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_464 = 464;

	/**
	111010001
	<pre><code>
	1,1,1,
	0,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_465 = 465;

	/**
	111010010
	<pre><code>
	1,1,1,
	0,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_466 = 466;

	/**
	111010011
	<pre><code>
	1,1,1,
	0,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_467 = 467;

	/**
	111010100
	<pre><code>
	1,1,1,
	0,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_468 = 468;

	/**
	111010101
	<pre><code>
	1,1,1,
	0,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_469 = 469;

	/**
	111010110
	<pre><code>
	1,1,1,
	0,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_470 = 470;

	/**
	111010111
	<pre><code>
	1,1,1,
	0,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_471 = 471;

	/**
	111011000
	<pre><code>
	1,1,1,
	0,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_472 = 472;

	/**
	111011001
	<pre><code>
	1,1,1,
	0,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_473 = 473;

	/**
	111011010
	<pre><code>
	1,1,1,
	0,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_474 = 474;

	/**
	111011011
	<pre><code>
	1,1,1,
	0,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_475 = 475;

	/**
	111011100
	<pre><code>
	1,1,1,
	0,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_476 = 476;

	/**
	111011101
	<pre><code>
	1,1,1,
	0,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_477 = 477;

	/**
	111011110
	<pre><code>
	1,1,1,
	0,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_478 = 478;

	/**
	111011111
	<pre><code>
	1,1,1,
	0,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_479 = 479;

	/**
	111100000
	<pre><code>
	1,1,1,
	1,0,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_480 = 480;

	/**
	111100001
	<pre><code>
	1,1,1,
	1,0,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_481 = 481;

	/**
	111100010
	<pre><code>
	1,1,1,
	1,0,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_482 = 482;

	/**
	111100011
	<pre><code>
	1,1,1,
	1,0,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_483 = 483;

	/**
	111100100
	<pre><code>
	1,1,1,
	1,0,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_484 = 484;

	/**
	111100101
	<pre><code>
	1,1,1,
	1,0,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_485 = 485;

	/**
	111100110
	<pre><code>
	1,1,1,
	1,0,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_486 = 486;

	/**
	111100111
	<pre><code>
	1,1,1,
	1,0,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_487 = 487;

	/**
	111101000
	<pre><code>
	1,1,1,
	1,0,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_488 = 488;

	/**
	111101001
	<pre><code>
	1,1,1,
	1,0,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_489 = 489;

	/**
	111101010
	<pre><code>
	1,1,1,
	1,0,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_490 = 490;

	/**
	111101011
	<pre><code>
	1,1,1,
	1,0,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_491 = 491;

	/**
	111101100
	<pre><code>
	1,1,1,
	1,0,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_492 = 492;

	/**
	111101101
	<pre><code>
	1,1,1,
	1,0,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_493 = 493;

	/**
	111101110
	<pre><code>
	1,1,1,
	1,0,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_494 = 494;

	/**
	111101111
	<pre><code>
	1,1,1,
	1,0,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_495 = 495;

	/**
	111110000
	<pre><code>
	1,1,1,
	1,1,0,
	0,0,0
	</code></pre>
	**/
	public static final int P_496 = 496;

	/**
	111110001
	<pre><code>
	1,1,1,
	1,1,0,
	0,0,1
	</code></pre>
	**/
	public static final int P_497 = 497;

	/**
	111110010
	<pre><code>
	1,1,1,
	1,1,0,
	0,1,0
	</code></pre>
	**/
	public static final int P_498 = 498;

	/**
	111110011
	<pre><code>
	1,1,1,
	1,1,0,
	0,1,1
	</code></pre>
	**/
	public static final int P_499 = 499;

	/**
	111110100
	<pre><code>
	1,1,1,
	1,1,0,
	1,0,0
	</code></pre>
	**/
	public static final int P_500 = 500;

	/**
	111110101
	<pre><code>
	1,1,1,
	1,1,0,
	1,0,1
	</code></pre>
	**/
	public static final int P_501 = 501;

	/**
	111110110
	<pre><code>
	1,1,1,
	1,1,0,
	1,1,0
	</code></pre>
	**/
	public static final int P_502 = 502;

	/**
	111110111
	<pre><code>
	1,1,1,
	1,1,0,
	1,1,1
	</code></pre>
	**/
	public static final int P_503 = 503;

	/**
	111111000
	<pre><code>
	1,1,1,
	1,1,1,
	0,0,0
	</code></pre>
	**/
	public static final int P_504 = 504;

	/**
	111111001
	<pre><code>
	1,1,1,
	1,1,1,
	0,0,1
	</code></pre>
	**/
	public static final int P_505 = 505;

	/**
	111111010
	<pre><code>
	1,1,1,
	1,1,1,
	0,1,0
	</code></pre>
	**/
	public static final int P_506 = 506;

	/**
	111111011
	<pre><code>
	1,1,1,
	1,1,1,
	0,1,1
	</code></pre>
	**/
	public static final int P_507 = 507;

	/**
	111111100
	<pre><code>
	1,1,1,
	1,1,1,
	1,0,0
	</code></pre>
	**/
	public static final int P_508 = 508;

	/**
	111111101
	<pre><code>
	1,1,1,
	1,1,1,
	1,0,1
	</code></pre>
	**/
	public static final int P_509 = 509;

	/**
	111111110
	<pre><code>
	1,1,1,
	1,1,1,
	1,1,0
	</code></pre>
	**/
	public static final int P_510 = 510;

	/**
	111111111
	<pre><code>
	1,1,1,
	1,1,1,
	1,1,1
	</code></pre>
	**/
	public static final int P_511 = 511;



	
	// ************************************************************************
	// MAIN
	// ************************************************************************

	public static void generateIntConstantDeclarations(){
		
		// print 2^9 (512) constants declarations.
		int cellType = 1;
		int len = (int) Math.pow(2, 9);
		for (int i = 0; i < len; i++) {
			String binary = String.format("%1$9s", Integer.toBinaryString(i)).replace(' ', '0');
			String pattern = new StringBuffer(binary)
					.insert(8, ',').insert(7, ',').insert(6, '\n')
					.insert(6, ',').insert(5, ',').insert(4, ',')
					.insert(3, '\n').insert(3, ',').insert(2, ',')
					.insert(1, ',').toString();
			System.out.printf("/**\n%s\n<pre><code>\n%s\n</code></pre>\n**/\n", binary, pattern);
			//System.out.printf("public static final int P_%d = getIntFromBinaryString(getStringFromPattern(new byte[]{\n%s\n}, %d));\n\n", i, pattern, cellType);		
			System.out.printf("public static final int P_%d = %d;\n\n", i, i);		
		}	
	}
			
	public static void main(String[] args){
		generateIntConstantDeclarations();
		/*
		System.out.println(P_0);
			System.out.println(P_1);
			System.out.println(P_2);
			System.out.println(P_3);
			System.out.println(P_4);
			System.out.println(P_511);
			*/
	}
}
