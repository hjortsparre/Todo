package todo.data.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeIdValidator implements
		ConstraintValidator<EmployeeId, String> {

	@Override
	public void initialize(EmployeeId employeeNumber) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		// Could be done with regexp, but for clarity sake
		if (value != null) {
			if (value.length() != 9) {
				return false;
			}
			if (value.split("-").length != 2) {
				return false;
			}
			if (value.split("-")[0].length() != 4) {
				return false;
			}
			if (value.split("-")[1].length() != 4) {
				return false;
			}
		}

		return true;

	}

}
