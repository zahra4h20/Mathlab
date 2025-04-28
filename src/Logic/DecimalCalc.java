package Logic;

public class DecimalCalc {

    // تقسیم اعداد به بخش صحیح و اعشار
    private static String[] splitDecimal(String num) {
        if (!num.contains(".")) return new String[]{num, "0"};
        String[] parts = num.split("\\.");
        return new String[]{parts[0], parts[1]};
    }

    private static String padRight(String s, int len) {
        while (s.length() < len) s += "0";
        return s;
    }

    private static String padLeft(String s, int len) {
        while (s.length() < len) s = "0" + s;
        return s;
    }


    // متد جمع
    public static String add(String a, String b) {
        String[] aParts = splitDecimal(a);
        String[] bParts = splitDecimal(b);

        int fracLen = Math.max(aParts[1].length(), bParts[1].length());
        String aFrac = padRight(aParts[1], fracLen);
        String bFrac = padRight(bParts[1], fracLen);

        String intSum = BigNumberCalc.add(aParts[0], bParts[0]);
        String fracSum = BigNumberCalc.add(aFrac, bFrac);

        if (fracSum.length() > fracLen) {
            // انتقال رقم اضافه به بخش صحیح
            intSum = BigNumberCalc.add(intSum, "1");
            fracSum = fracSum.substring(1);
        }

        return intSum + "." + fracSum;
    }

    // متد تفریق
    public static String subtract(String a, String b) {
        String[] aParts = splitDecimal(a);
        String[] bParts = splitDecimal(b);

        int fracLen = Math.max(aParts[1].length(), bParts[1].length());
        String aFrac = padRight(aParts[1], fracLen);
        String bFrac = padRight(bParts[1], fracLen);

        // اگر بخش اعشاری a کوچکتر بود، یکی از بخش صحیح قرض می‌گیریم
        if (BigNumberCalc.isSmaller(aFrac, bFrac)) {
            aParts[0] = BigNumberCalc.subtract(aParts[0], "1");
            aFrac = BigNumberCalc.add(aFrac, padRight("1", fracLen)); // معادل قرض گرفتن 1 در اعشار
        }

        String intPart = BigNumberCalc.subtract(aParts[0], bParts[0]);
        String fracPart = BigNumberCalc.subtract(aFrac, bFrac);

        return intPart + "." + padLeft(fracPart, fracLen);
    }

    //متد ضرب
    public static String multiply(String a, String b) {
        String[] aParts = splitDecimal(a);
        String[] bParts = splitDecimal(b);

        int totalDecimal = aParts[1].length() + bParts[1].length();

        String aFull = aParts[0] + aParts[1];
        String bFull = bParts[0] + bParts[1];

        String product = BigNumberCalc.multiply(aFull, bFull);

        if (totalDecimal == 0) return product;

        while (product.length() <= totalDecimal)
            product = "0" + product;

        int split = product.length() - totalDecimal;
        return product.substring(0, split) + "." + product.substring(split);
    }

    //متد تقسیم
    public static String divide(String a, String b, int precision) {

        String[] aParts = splitDecimal(a);
        String[] bParts = splitDecimal(b);

        int aLen = aParts[1].length();
        int bLen = bParts[1].length();

        int shift = precision + bLen - aLen;

        String aFull = aParts[0] + aParts[1];
        for (int i = 0; i < shift; i++) aFull += "0";

        String bFull = bParts[0] + bParts[1];

        String result = BigNumberCalc.divide(aFull, bFull);

        if (result.length() <= precision) {
            while (result.length() < precision)
                result = "0" + result;
            result = "0." + result;
        } else {
            int split = result.length() - precision;
            result = result.substring(0, split) + "." + result.substring(split);
        }

        return result;
    }

    //متد توان
    public static String power(String base, String expStr) {
        int exp = Integer.parseInt(expStr);
        String result = "1";

        for (int i = 0; i < exp; i++) {
            result = multiply(result, base);
        }

        return result;
    }

    public static String evaluate(String expression) {
        expression = expression.replaceAll(" ", "");

        if (expression.contains("+")) {
            String[] parts = expression.split("\\+");
            return add(parts[0], parts[1]);
        } else if (expression.contains("-")) {
            String[] parts = expression.split("-");
            return subtract(parts[0], parts[1]);
        } else if (expression.contains("*")) {
            String[] parts = expression.split("\\*");
            return multiply(parts[0], parts[1]);
        } else if (expression.contains("/")) {
            String[] parts = expression.split("/");
            return divide(parts[0], parts[1], 10);
        } else if (expression.contains("^")) {
            String[] parts = expression.split("\\^");
            return power(parts[0], parts[1]);
        }

        return expression;
    }
}