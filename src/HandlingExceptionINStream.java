import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandlingExceptionINStream {

	public static void main(String[] args) {
		List<String> urlList = new ArrayList<>();
		urlList.add("S");
		urlList.add("W");
		urlList.add(null);
		answerRequestFunc(urlList);
		//System.out.println(encodedAddressUsingWrapper(urlList));
		readFileFromPath();

	}

	public static String encodedAddressUsingWrapper(List<String> list) {
		return list.stream().map(wrapper(s -> URLEncoder.encode(s, "UTF-8"))).collect(Collectors.joining(","));
	}

	public static void answerRequestFunc(List<String> list) {
		Stream<CheckedExceptionResult> result = list.stream().map(wrapper2(s -> URLEncoder.encode(s, "UTF-8")));
		result.forEach(r -> {
			if (r.isFailure()) {
				// report failures
				r.getException().printStackTrace();
			} else {
				// report success
				System.out.println(r.getValue());
			}
		});
	}
	
	public static void readFileFromPath() {
		List<Path> pathList = List.of(Path.of("..."), Path.of("..."), Path.of("..."));

		List<CheckedExceptionResult> listResults = pathList.stream()
		    .map(wrapper2(Files::readString))
		    .toList();
		listResults.forEach(r -> {
			if (r.isFailure()) {
				// report failures
				r.getException().printStackTrace();
			} else {
				// report success
				System.out.println(r.getValue());
			}
		});
	}
	
	private static <T, R, E extends Exception> Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
		return arg -> {
			try {
				return fe.apply(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
	
	private static <T, R, E extends Exception> Function<T, CheckedExceptionResult> wrapper2(FunctionWithException<T, R, E> fe) {
		return arg -> {
			try {
				return new Success(fe.apply(arg));
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}
	

}
