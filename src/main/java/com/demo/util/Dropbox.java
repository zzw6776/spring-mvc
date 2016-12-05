package com.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.NetworkIOException;
import com.dropbox.core.RetryException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxPathV2;
import com.dropbox.core.v2.files.CommitInfo;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.UploadSessionCursor;
import com.dropbox.core.v2.files.UploadSessionFinishErrorException;
import com.dropbox.core.v2.files.UploadSessionLookupErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;

/**
 * An example command-line application that runs through the web-based OAuth
 * flow (using {@link DbxWebAuth}).
 */
public class Dropbox {
	// Adjust the chunk size based on your network speed and reliability. Larger
	// chunk sizes will
	// result in fewer network requests, which will be faster. But if an error
	// occurs, the entire
	// chunk will be lost and have to be re-uploaded. Use a multiple of 4MiB for
	// your chunk size.
	private static final long CHUNKED_UPLOAD_CHUNK_SIZE = 8L << 20; // 8MiB
	private static final int CHUNKED_UPLOAD_MAX_ATTEMPTS = 5;

	public static void main(String[] args) throws Exception {
		DbxRequestConfig requestConfig = new DbxRequestConfig("examples-authorize");
		DbxClientV2 dbxClient = new DbxClientV2(requestConfig, "accessToken",DbxHost.DEFAULT);
		String metadatas = dbxClient.files().listFolderBuilder("").withRecursive(true).start().toStringMultiline();

				
			System.out.println(metadatas);
	
	}
	
	public static void upload(String accessToken, String localPath, String dropboxPath) throws IOException {

		// String localPath = "D:\\2.bmp";
		// String dropboxPath = "/dropbox-path/file.txt";

		// Read auth info file.
		DbxAuthInfo authInfo = new DbxAuthInfo(accessToken, DbxHost.DEFAULT);

		String pathError = DbxPathV2.findError(dropboxPath);
		if (pathError != null) {
			System.err.println("Invalid <dropbox-path>: " + pathError);
			
			return;
		}

		File localFile = new File(localPath);
		if (!localFile.exists()) {
			System.err.println("Invalid <local-path>: file does not exist.");
			
			return;
		}

		if (!localFile.isFile()) {
			System.err.println("Invalid <local-path>: not a file.");
			
			return;
		}

		// Create a DbxClientV2, which is what you use to make API calls.
		DbxRequestConfig requestConfig = new DbxRequestConfig("examples-upload-file");
		DbxClientV2 dbxClient = new DbxClientV2(requestConfig, authInfo.getAccessToken(), authInfo.getHost());

		// upload the file with simple upload API if it is small enough,
		// otherwise use chunked
		// upload API for better performance. Arbitrarily chose 2 times our
		// chunk size as the
		// deciding factor. This should really depend on your network.
		if (localFile.length() <= (2 * CHUNKED_UPLOAD_CHUNK_SIZE)) {
			uploadFile(dbxClient, localFile, dropboxPath);
		} else {
			chunkedUploadFile(dbxClient, localFile, dropboxPath);
		}

	}

	public static DbxAuthInfo Auth(String key, String secret) throws IOException, DbxException {
		// Only display important log messages.
		Logger.getLogger("").setLevel(Level.WARNING);
		// Read app info file (contains app key and app secret)
		DbxAppInfo appInfo = new DbxAppInfo(key, secret);
		// Run through Dropbox API authorization process
		DbxRequestConfig requestConfig = new DbxRequestConfig("examples-authorize");
		DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
		DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder().withNoRedirect().build();
		String authorizeUrl = webAuth.authorize(webAuthRequest);
		System.out.println("1. Go to " + authorizeUrl);
		System.out.println("2. Click \"Allow\" (you might have to log in first).");
		System.out.println("3. Copy the authorization code.");
		System.out.print("Enter the authorization code here: ");
		String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
		if (code == null) {
			
			return null;
		}
		code = code.trim();

		DbxAuthFinish authFinish;
		authFinish = webAuth.finishFromCode(code);
		System.out.println("Authorization complete.");
		System.out.println("- User ID: " + authFinish.getUserId());
		System.out.println("- Access Token: " + authFinish.getAccessToken());

		// Save auth information to output file.
		DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.getAccessToken(), appInfo.getHost());

		return authInfo;
	}

	public static FullAccount authInfo() throws DbxApiException, DbxException {
		DbxAuthInfo authInfo = new DbxAuthInfo("accessToken",
				DbxHost.DEFAULT);
		// Create a DbxClientV1, which is what you use to make API calls.
		DbxRequestConfig requestConfig = new DbxRequestConfig("examples-account-info");
		DbxClientV2 dbxClient = new DbxClientV2(requestConfig, authInfo.getAccessToken(), authInfo.getHost());

		// Make the /account/info API call.
		FullAccount dbxAccountInfo;
		dbxAccountInfo = dbxClient.users().getCurrentAccount();

		System.out.print(dbxAccountInfo.toStringMultiline());

		return dbxAccountInfo;
	}

	/**
	 * Uploads a file in a single request. This approach is preferred for small
	 * files since it eliminates unnecessary round-trips to the servers.
	 *
	 * @param dbxClient
	 *            Dropbox user authenticated client
	 * @param localFIle
	 *            local file to upload
	 * @param dropboxPath
	 *            Where to upload the file to within Dropbox
	 */
	private static void uploadFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
		try (InputStream in = new FileInputStream(localFile)) {
			FileMetadata metadata = dbxClient.files().uploadBuilder(dropboxPath).withMode(WriteMode.ADD)
					.withClientModified(new Date(localFile.lastModified())).uploadAndFinish(in);

			System.out.println(metadata.toStringMultiline());
		} catch (UploadErrorException ex) {
			System.err.println("Error uploading to Dropbox: " + ex.getMessage());
			
		} catch (DbxException ex) {
			System.err.println("Error uploading to Dropbox: " + ex.getMessage());
			
		} catch (IOException ex) {
			System.err.println("Error reading from file \"" + localFile + "\": " + ex.getMessage());
			
		}
	}

	/**
	 * Uploads a file in chunks using multiple requests. This approach is
	 * preferred for larger files since it allows for more efficient processing
	 * of the file contents on the server side and also allows partial uploads
	 * to be retried (e.g. network connection problem will not cause you to
	 * re-upload all the bytes).
	 *
	 * @param dbxClient
	 *            Dropbox user authenticated client
	 * @param localFIle
	 *            local file to upload
	 * @param dropboxPath
	 *            Where to upload the file to within Dropbox
	 */
	private static void chunkedUploadFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
		long size = localFile.length();

		// assert our file is at least the chunk upload size. We make this
		// assumption in the code
		// below to simplify the logic.
		if (size < CHUNKED_UPLOAD_CHUNK_SIZE) {
			System.err.println("File too small, use upload() instead.");
			
			return;
		}

		long uploaded = 0L;
		DbxException thrown = null;

		// Chunked uploads have 3 phases, each of which can accept uploaded
		// bytes:
		//
		// (1) Start: initiate the upload and get an upload session ID
		// (2) Append: upload chunks of the file to append to our session
		// (3) Finish: commit the upload and close the session
		//
		// We track how many bytes we uploaded to determine which phase we
		// should be in.
		String sessionId = null;
		for (int i = 0; i < CHUNKED_UPLOAD_MAX_ATTEMPTS; ++i) {
			if (i > 0) {
				System.out.printf("Retrying chunked upload (%d / %d attempts)\n", i + 1, CHUNKED_UPLOAD_MAX_ATTEMPTS);
			}

			try (InputStream in = new FileInputStream(localFile)) {
				// if this is a retry, make sure seek to the correct offset
				in.skip(uploaded);

				// (1) Start
				if (sessionId == null) {
					sessionId = dbxClient.files().uploadSessionStart().uploadAndFinish(in, CHUNKED_UPLOAD_CHUNK_SIZE)
							.getSessionId();
					uploaded += CHUNKED_UPLOAD_CHUNK_SIZE;
					printProgress(uploaded, size);
				}

				UploadSessionCursor cursor = new UploadSessionCursor(sessionId, uploaded);

				// (2) Append
				while ((size - uploaded) > CHUNKED_UPLOAD_CHUNK_SIZE) {
					dbxClient.files().uploadSessionAppendV2(cursor).uploadAndFinish(in, CHUNKED_UPLOAD_CHUNK_SIZE);
					uploaded += CHUNKED_UPLOAD_CHUNK_SIZE;
					printProgress(uploaded, size);
					cursor = new UploadSessionCursor(sessionId, uploaded);
				}

				// (3) Finish
				long remaining = size - uploaded;
				CommitInfo commitInfo = CommitInfo.newBuilder(dropboxPath).withMode(WriteMode.ADD)
						.withClientModified(new Date(localFile.lastModified())).build();
				FileMetadata metadata = dbxClient.files().uploadSessionFinish(cursor, commitInfo).uploadAndFinish(in,
						remaining);

				System.out.println(metadata.toStringMultiline());
				return;
			} catch (RetryException ex) {
				thrown = ex;
				// RetryExceptions are never automatically retried by the client
				// for uploads. Must
				// catch this exception even if DbxRequestConfig.getMaxRetries()
				// > 0.
				sleepQuietly(ex.getBackoffMillis());
				continue;
			} catch (NetworkIOException ex) {
				thrown = ex;
				// network issue with Dropbox (maybe a timeout?) try again
				continue;
			} catch (UploadSessionLookupErrorException ex) {
				if (ex.errorValue.isIncorrectOffset()) {
					thrown = ex;
					// server offset into the stream doesn't match our offset
					// (uploaded). Seek to
					// the expected offset according to the server and try
					// again.
					uploaded = ex.errorValue.getIncorrectOffsetValue().getCorrectOffset();
					continue;
				} else {
					// Some other error occurred, give up.
					System.err.println("Error uploading to Dropbox: " + ex.getMessage());
					
					return;
				}
			} catch (UploadSessionFinishErrorException ex) {
				if (ex.errorValue.isLookupFailed() && ex.errorValue.getLookupFailedValue().isIncorrectOffset()) {
					thrown = ex;
					// server offset into the stream doesn't match our offset
					// (uploaded). Seek to
					// the expected offset according to the server and try
					// again.
					uploaded = ex.errorValue.getLookupFailedValue().getIncorrectOffsetValue().getCorrectOffset();
					continue;
				} else {
					// some other error occurred, give up.
					System.err.println("Error uploading to Dropbox: " + ex.getMessage());
					
					return;
				}
			} catch (DbxException ex) {
				System.err.println("Error uploading to Dropbox: " + ex.getMessage());
				
				return;
			} catch (IOException ex) {
				System.err.println("Error reading from file \"" + localFile + "\": " + ex.getMessage());
				
				return;
			}
		}

		// if we made it here, then we must have run out of attempts
		System.err.println("Maxed out upload attempts to Dropbox. Most recent error: " + thrown.getMessage());
		
	}

	private static void printProgress(long uploaded, long size) {
		System.out.printf("Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
	}

	private static void sleepQuietly(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			// just exit
			System.err.println("Error uploading to Dropbox: interrupted during backoff.");
			
		}
	}

	

}