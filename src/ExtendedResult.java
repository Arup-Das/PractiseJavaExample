abstract class CheckedExceptionResult<R> {
    public abstract boolean isSuccess();
    public abstract boolean isFailure();
    public abstract R getValue();
    public abstract Exception getException();
    
}

class Success<R> extends CheckedExceptionResult<R> {

    private R value;

    public Success(R i) {
        this.value = i;
    }

	@Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public Exception getException() {
        return null;
    }

	@Override
	public R getValue() {
		return value;
	}
}

class Failure<R> extends CheckedExceptionResult<R> {

    public Exception excp;

    public Failure(Exception e) {
        this.excp = e;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public Exception getException() {
        return this.excp;
    }

	@Override
	public R getValue() {
		return null;
	}
}
