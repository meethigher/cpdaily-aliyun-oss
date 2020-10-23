package aliyun;

import java.io.File;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;

public class PutObjectProgressListener2 implements ProgressListener {
	private long bytesWritten = 0;
	private long totalBytes = -1;
	private boolean succeed = false;

	@Override
	public void progressChanged(ProgressEvent progressEvent) {
		long bytes = progressEvent.getBytes();
		ProgressEventType eventType = progressEvent.getEventType();
		switch (eventType) {
		case TRANSFER_STARTED_EVENT:
			System.out.println("Start to upload......");
			break;
		case REQUEST_CONTENT_LENGTH_EVENT:
			this.totalBytes = bytes;
			System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
			break;
		case REQUEST_BYTE_TRANSFER_EVENT:
			this.bytesWritten += bytes;
			if (this.totalBytes != -1) {
				int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
				System.out.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%("
						+ this.bytesWritten + "/" + this.totalBytes + ")");
			} else {
				System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "("
						+ this.bytesWritten + "/...)");
			}
			break;
		case TRANSFER_COMPLETED_EVENT:
			this.succeed = true;
			System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
			System.out.println("https://wecres.cpdaily.com/" + Data.objectName);
			break;
		case TRANSFER_FAILED_EVENT:
			System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
			break;
		default:
			break;
		}
	}

	public boolean isSucceed() {
		return succeed;
	}

	public static void upload() {
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(Data.endpoint, Data.accessKeyId, Data.accessKeySecret);

		// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
		PutObjectRequest putObjectRequest = new PutObjectRequest(Data.bucketName, Data.objectName,
				new File(Data.localFile));
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
		metadata.setHeader(OSSHeaders.OSS_SECURITY_TOKEN, Data.securityToken);
		metadata.setObjectAcl(CannedAccessControlList.PublicReadWrite);

		putObjectRequest.setMetadata(metadata);
		try {
			// 上传文件的同时指定了进度条参数。
			ossClient.putObject(
					putObjectRequest.<PutObjectRequest>withProgressListener(new PutObjectProgressListener2()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 关闭OSSClient。
		ossClient.shutdown();
	}

	public static void main(String[] args) {

	}
}
