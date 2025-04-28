package Logic;

public class BigNumberCalc {


   //جمع
    public static String add(String num1, String num2) {
        StringBuilder result = new StringBuilder();

        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = 0;
            int digit2 = 0;

            if (i >= 0) {
                digit1 = num1.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                digit2 = num2.charAt(j) - '0';
                j--;
            }


            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            result.append(sum % 10);
        }

        return result.reverse().toString();
    }

    //تفریق
    public static String subtract(String num1, String num2) {
        boolean negative = false;
        if (isSmaller(num1, num2)) {
            String temp = num1;
            num1 = num2;
            num2 = temp;
            negative = true;
        }

        StringBuilder result = new StringBuilder();

        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int borrow = 0;

        while (i >= 0 || j >= 0 || borrow > 0) {
            int digit1 = 0;
            int digit2 = 0;

            if (i >= 0) {
                digit1 = num1.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                digit2 = num2.charAt(j) - '0';
                j--;
            }


            digit1 -= borrow;

            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(digit1 - digit2);
        }


        while (result.length() > 1 && result.charAt(result.length() - 1) == '0')
            result.deleteCharAt(result.length() - 1);

        if (negative) result.append('-');

        return result.reverse().toString();
    }

    //ضرب
    public static String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        int[] result = new int[len1 + len2];

        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = mul + result[i + j + 1];

                result[i + j] += sum / 10;
                result[i + j + 1] = sum % 10;
            }
        }

        String answer = "";

        for (int val : result) {
            if (answer.equals("") && val == 0) {
                continue;
            }
            answer += val;
        }

        if (answer.equals("")) {
            return "0";
        }

        return answer;

    }

    static boolean isSmaller(String num1, String num2) {
        if (num1.length() < num2.length()) return true;
        if (num1.length() > num2.length()) return false;
        return num1.compareTo(num2) < 0;
    }


    // تقسسیم
    public static String divide(String dividend, String divisor) {
        // حذف صفرهای اضافی اول
        dividend = removeLeadingZeros(dividend);
        divisor = removeLeadingZeros(divisor);

        if (divisor.equals("0")) return "خطا";
        if (compare(dividend, divisor) < 0) return "0";

        StringBuilder result = new StringBuilder();
        String current = "";

        int i = 0;
        while (i < dividend.length()) {
            current += dividend.charAt(i);
            current = removeLeadingZeros(current);
            int count = 0;

            while (compare(current, divisor) >= 0) {
                current = subtract(current, divisor);
                count++;
            }

            result.append(count);
            i++;
        }

        return removeLeadingZeros(result.toString());
    }
    public static String removeLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }
    public static int compare(String a, String b) {
        a = removeLeadingZeros(a);
        b = removeLeadingZeros(b);

        if (a.length() > b.length()) return 1;
        if (a.length() < b.length()) return -1;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) > b.charAt(i)) return 1;
            if (a.charAt(i) < b.charAt(i)) return -1;
        }
        return 0;
    }


    //توان
    public static String power(String base, String exponentStr) {
        String result = "1";
        int exponent = Integer.parseInt(exponentStr);

        for (int i = 0; i < exponent; i++) {
            result = multiply(result, base);
        }

        return result;
    }


    //فاکتوریل
    public static String factorial(String numStr) {
        int n = Integer.parseInt(numStr);
        String result = "1";

        for (int i = 2; i <= n; i++) {
            result = multiply(result, Integer.toString(i));
        }

        return result;
    }

    //
    public static String evaluate(String expression) {
        expression = expression.replaceAll(" ", "");

        if (expression.contains("^")) {
            String[] parts = expression.split("\\^");
            return power(parts[0], parts[1]);
        } else if (expression.contains("*")) {
            String[] parts = expression.split("\\*");
            return multiply(parts[0], parts[1]);
        } else if (expression.contains("/")) {
            String[] parts = expression.split("\\/");
            return divide(parts[0], parts[1]);
        } else if (expression.contains("+")) {
            String[] parts = expression.split("\\+");
            return add(parts[0], parts[1]);
        }
        else if (expression.contains("-")) {
            String[] parts = expression.split("-");
            return subtract(parts[0], parts[1]);}

        else if (expression.contains("!")) {
            String part = expression.replace("!", "");
            return factorial(part);
        }

        return expression;
    }

}
