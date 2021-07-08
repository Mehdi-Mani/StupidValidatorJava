package validation;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    String field;
    Boolean bail;
    Vector<String> errors;
    Pattern pattern;
    Matcher matcher;


    Validator(String field, Boolean bail) {
        this.field = field;
        this.bail = bail;
        this.errors = new Vector<String>();
    }

    public boolean Validate() {
        if (this.errors.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrors() {
        String temp = "";
        for (String error : errors) {
            temp += error + "\n";
        }
        return temp;
    }

    public Boolean haveToBail() {
        return this.bail == true && !this.errors.isEmpty();
    }

    public Validator isRequired() {
        if (!haveToBail()) {
            if (this.field.isEmpty()) {
                this.errors.add("This field is required.");
            }
        }
        return this;
    }

    public Validator isAlpha() {
        if (!haveToBail()) {
            this.pattern = Pattern.compile("[^A-Za-z]", Pattern.CASE_INSENSITIVE);
            this.matcher = pattern.matcher(this.field);
            if (matcher.find()) {
                this.errors.add("This field must Alpha.");
            }
        }
        return this;
    }

    public Validator isNum() {
        if (!haveToBail()) {
            this.pattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
            this.matcher = pattern.matcher(this.field);
            if (matcher.find()) {
                this.errors.add("This field must Num.");
            }
        }
        return this;
    }

    public Validator isAlphaNum() {
        if (!haveToBail()) {
            this.pattern = Pattern.compile("[^A-Za-z0-9_ ]", Pattern.CASE_INSENSITIVE);
            this.matcher = pattern.matcher(this.field);
            if (matcher.find()) {
                this.errors.add("This field must AlphaNumerical.");
            }
        }
        return this;
    }

    public Validator isEmail() {
        if (!haveToBail()) {
            String regex = "^(.+)@(.+)$";
            this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            this.matcher = pattern.matcher(this.field);
            if (matcher.find()) {
                this.errors.add("This field must a valid Email.");
            }
        }
        return this;
    }

    public Validator isPhoneNumber() {
        if (!haveToBail()) {
            String regex =
                    "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
            this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            this.matcher = pattern.matcher(this.field);
            if (matcher.find()) {
                this.errors.add("This field must a valid Phone Number.");
            }
        }
        return this;
    }

    public Validator max(int max) {
        if (!haveToBail()) {
            int x = Integer.parseInt(this.field);
            if (x > max) {
                this.errors.add("This field is higher than max value.");
            }
        }
        return this;
    }

    public Validator min(int min) {
        if (!haveToBail()) {
            int x = Integer.parseInt(this.field);
            if (x < min) {
                this.errors.add("This field is lower than min value.");
            }
        }
        return this;
    }

    public Validator between(int min, int max) {
        if (!haveToBail()) {
            int x = Integer.parseInt(this.field);
            if (x < min || x > max) {
                this.errors.add("This field must be between max and min value.");
            }
        }
        return this;
    }

    public Validator isEqual(String toCompare, Boolean isCaseSensitive) {
        if (!haveToBail()) {
            Boolean hasErrors = false;
            if (isCaseSensitive == true) {
                if (!this.field.equals(toCompare)) {
                    hasErrors = true;
                }
            } else {
                if (!this.field.toUpperCase().equals(toCompare.toUpperCase())) {
                    hasErrors = true;
                }
            }
            if (hasErrors) {
                this.errors.add("This field doesn't not meet the Equal rule.");
            }
        }
        return this;
    }

    public Validator CustomValidation(String regex, String error) {
        if (!haveToBail()) {
            String reg = regex;
            this.pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
            this.matcher = pattern.matcher(this.field);
            if (matcher.find()) {
                this.errors.add(error);
            }
        }
        return this;
    }

    public static void main(String[] args) {
        Validator vl = new Validator("11df", true);
        if (vl.isNum().max(10).Validate() == true) {
            System.out.println("Tout est valide");
        } else {
            System.out.println(vl.getErrors());
        }
    }
}
