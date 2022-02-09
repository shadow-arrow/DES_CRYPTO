public class Solution {

    public String convertHexToBinary(String input) {
        try {
            int size = input.length() * 4;
            input = Long.toBinaryString(Long.parseUnsignedLong(input, 16));
            while (input.length() < size) {
                input = "0" + input;
            }
            return input;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String convertBinaryToHex(String input) {
        try {
            int size = input.length() / 4;
            input = Long.toHexString(Long.parseUnsignedLong(input, 2));
            while (input.length() < size) {
                input = "0" + input;
            }
            return input;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Hàm hoán vị theo các quy tắc PC-1, PC-2
     *
     * @param sequence
     * @param input
     * @return
     */

    public String permute(int[] sequence, String input) {
        String result = "";
        try {
            input = convertHexToBinary(input);
            for (int i = 0; i < sequence.length; i++) {
                result += input.charAt(sequence[i] - 1);
            }
            result = convertBinaryToHex(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * H tính toán phép xor
     *
     * @param strOne
     * @param strTwo
     * @return
     */
    public String handleXOR(String strOne, String strTwo) {
        String result = "";
        try {
            long numberA = Long.parseUnsignedLong(strOne, 16); // parse về hệ cơ số 10
            long numberB = Long.parseUnsignedLong(strTwo, 16);
            numberA = numberA ^ numberB;   // xor
            result = Long.toHexString(numberA);
            while (result.length() < strTwo.length()) {
                result = "0" + result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Ham dich chuyen bit
     *
     * @param input
     * @param numBits
     * @return
     */
    public String moveBits(String input, int numBits) {
        String result = "";
        try {
            String context = convertHexToBinary(input);
            result = context.substring(numBits) + context.substring(0, numBits);
            result = convertBinaryToHex(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Hàm tính toán 16 key
     *
     * @param key
     * @return
     */

    public String[] generateKeys(String key) {
        String keys[] = new String[16];
        String left, right;
        try {
            key = permute(Sample.PC1, key);
            for (int i = 0; i < 16; i++) {
                left = key.substring(0, 7);
                right = key.substring(7, 14);
                key = moveBits(left, Sample.shiftBits[i]) + moveBits(right, Sample.shiftBits[i]);
                keys[i] = permute(Sample.PC2, key);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return keys;
    }


    public String searchValueInSBox(String input) {
        String output = "";
        try {
            input = convertHexToBinary(input);
            for (int i = 0; i < 48; i += 6) {
                String temp = input.substring(i, i + 6);
                int num = i / 6;
                int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
                int col = Integer.parseInt(temp.substring(1, 5), 2);
                output += Integer.toHexString(Sample.sbox[num][row][col]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }



    public String round(String input, String key) {
        try {
            String left = input.substring(0, 8);
            String right = input.substring(8, 16);
            String temp = right;
            temp = permute(Sample.EP, temp);
            temp = handleXOR(temp, key);
            temp = searchValueInSBox(temp);
            temp = permute(Sample.P, temp);
            left = handleXOR(left, temp);
            return right + left;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String encrypt(String plainText, String key) {
        try {
            String keys[] = generateKeys(key);
            plainText = permute(Sample.IP, plainText);
            for (int i = 0; i < 16; i++) {
                plainText = round(plainText, keys[i]);
            }
            plainText = plainText.substring(8, 16) + plainText.substring(0, 8);
            plainText = permute(Sample.IP1, plainText);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return plainText;
    }

    public String decrypt(String plainText, String key) {
        try {
            String keys[] = generateKeys(key);
            plainText = permute(Sample.IP, plainText);
            for (int i = 15; i >= 0; i--) {
                plainText = round(plainText, keys[i]);
            }
            plainText = plainText.substring(8, 16) + plainText.substring(0, 8);
            plainText = permute(Sample.IP1, plainText);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return plainText;
    }
}


