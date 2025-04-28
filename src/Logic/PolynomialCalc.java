package Logic;

public class PolynomialCalc {

    // متدی که یک رشته چندجمله‌ای را به آرایه‌ای از ضرایب تبدیل می‌کند

    private static int[] parseToCoeffs(String poly) {
        // حذف فاصله‌ها
        poly = poly.replace(" ", "");

        if (poly.startsWith("-")) {
            poly = "-" + poly.substring(1).replace("-", "+-");
        } else {
            poly = poly.replace("-", "+-");
        }
        String[] parts = poly.split("\\+");

        // پیدا کردن بیشینه توان موجود در عبارت
        int maxExp = 0;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            int exp = 0;
            if (part.contains("x")) {
                if (part.contains("^")) {
                    int caretIndex = part.indexOf('^');
                    String expStr = part.substring(caretIndex + 1);
                    exp = Integer.parseInt(expStr); //تبدیل رشته به عدد صحیح
                } else {
                    exp = 1;
                }
            } else {
                exp = 0;
            }
            if (exp > maxExp) {
                maxExp = exp;
            }
        }

        // ایجاد آرایه‌ای به اندازه maxExp + 1 (از 0 تا maxExp)
        int[] coeffs = new int[maxExp + 1];



        for (String part : parts) {
            if (part.isEmpty()) continue;
            int coeff = 0, exp = 0;
            if (part.contains("x")) {
                int xIndex = part.indexOf("x");
                String coeffStr = part.substring(0, xIndex);
                if (coeffStr.equals("") || coeffStr.equals("+")) {
                    coeff = 1;
                } else if (coeffStr.equals("-")) {
                    coeff = -1;
                } else {
                    coeff = Integer.parseInt(coeffStr);
                }
                if (part.contains("^")) {
                    int caretIndex = part.indexOf('^');
                    String expStr = part.substring(caretIndex + 1);
                    exp = Integer.parseInt(expStr);
                } else {
                    exp = 1;
                }
            } else {
                coeff = Integer.parseInt(part);
                exp = 0;
            }
            coeffs[exp] += coeff;
        }
        return coeffs;
    }

    //  قالب‌بندی آرایه ضرایب به یک رشته چندجمله‌ای به فرم استاندارد
    private static String formatPolynomial(int[] coeffs) {
        StringBuilder sb = new StringBuilder();

        for (int i = coeffs.length - 1; i >= 0; i--) {
            int coeff = coeffs[i];
            if (coeff == 0) continue;

            if (sb.length() > 0) {
                if (coeff > 0)
                    sb.append(" + ");
                else
                    sb.append(" - ");
            } else {
                if (coeff < 0)
                    sb.append("-");
            }

            int absCoeff = Math.abs(coeff);
            if (i == 0) {
                sb.append(absCoeff);
            } else if (i == 1) {
                if (absCoeff != 1)
                    sb.append(absCoeff).append("x");
                else
                    sb.append("x");
            } else {
                if (absCoeff != 1)
                    sb.append(absCoeff).append("x^").append(i);
                else
                    sb.append("x^").append(i);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    // عملیات جمع دو چندجمله‌ای به صورت رشته
    public static String add(String poly1, String poly2) {
        int[] coeffs1 = parseToCoeffs(poly1);
        int[] coeffs2 = parseToCoeffs(poly2);
        int maxLen = Math.max(coeffs1.length, coeffs2.length);
        int[] result = new int[maxLen];

        for (int i = 0; i < maxLen; i++) {
            int c1 = i < coeffs1.length ? coeffs1[i] : 0;
            int c2 = i < coeffs2.length ? coeffs2[i] : 0;
            result[i] = c1 + c2;
        }
        return formatPolynomial(result);
    }

    // عملیات تفریق دو چندجمله‌ای به صورت رشته
    public static String subtract(String poly1, String poly2) {
        int[] coeffs1 = parseToCoeffs(poly1);
        int[] coeffs2 = parseToCoeffs(poly2);
        int maxLen = Math.max(coeffs1.length, coeffs2.length);
        int[] result = new int[maxLen];

        for (int i = 0; i < maxLen; i++) {
            int c1 = i < coeffs1.length ? coeffs1[i] : 0;
            int c2 = i < coeffs2.length ? coeffs2[i] : 0;
            result[i] = c1 - c2;
        }
        return formatPolynomial(result);
    }

    // عملیات ضرب دو چندجمله‌ای به صورت رشته
    public static String multiply(String poly1, String poly2) {
        int[] coeffs1 = parseToCoeffs(poly1);
        int[] coeffs2 = parseToCoeffs(poly2);
        int resultLength = coeffs1.length + coeffs2.length - 1;
        int[] result = new int[resultLength];

        for (int i = 0; i < coeffs1.length; i++) {
            for (int j = 0; j < coeffs2.length; j++) {
                result[i + j] += coeffs1[i] * coeffs2[j];
            }
        }
        return formatPolynomial(result);
    }
}
