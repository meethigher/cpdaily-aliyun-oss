/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package aliyun;

import java.io.File;
import java.io.IOException;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;

/**
 * Image process examples.
 *
 */
public class ImageSample {
	public static void main(String[] args) throws IOException {
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(Data.endpoint, Data.accessKeyId, Data.accessKeySecret);

		// 创建PutObjectRequest对象。
		PutObjectRequest putObjectRequest = new PutObjectRequest(Data.bucketName, Data.objectName,
				new File(Data.localFile));

		// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
		metadata.setHeader(OSSHeaders.OSS_SECURITY_TOKEN, Data.securityToken);
		metadata.setObjectAcl(CannedAccessControlList.PublicReadWrite);
		putObjectRequest.setMetadata(metadata);

		// 上传文件。
		ossClient.putObject(putObjectRequest);

		// 关闭OSSClient。
		ossClient.shutdown();
	}
}
