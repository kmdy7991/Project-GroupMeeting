package com.groupmeeting.test.unit.global.image;

import com.groupmeeting.core.exception.custom.NotImageRequestException;
import com.groupmeeting.global.image.S3ImageService;
import com.groupmeeting.test.base.object.MockitoTest;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

public class S3ImageServiceTest extends MockitoTest {
    @Mock
    private S3Template s3Template;

    @Spy
    @InjectMocks
    private S3ImageService s3ImageService;

    private final String FILENAME = "testImage.jpg";
    private final String FOLDER = "test";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(s3ImageService, "BUCKET", "test");
    }

    @Test
    @DisplayName("이미지를 저장하고 파일명을 반환한다.")
    void uploadImage() throws NotImageRequestException, IOException {
        MultipartFile file = new MockMultipartFile("file", FILENAME, "image/jpg", "test image content".getBytes());

        doReturn(FILENAME).when(s3ImageService).uploadImage(FOLDER, file);

        String result = s3ImageService.uploadImage(FOLDER, file);

        assertThat(result.endsWith("jpg")).isEqualTo(true);
    }

    @Test
    @DisplayName("콘텐츠 타입이 이미지가 아니라면 예외가 발생한다.")
    void uploadNotImage() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test image content".getBytes());

        assertThrows(NotImageRequestException.class,
                () -> s3ImageService.uploadImage(FOLDER, file));
    }

    @Test
    @DisplayName("저장된 이미지를 삭제할 수 있다.")
    void deleteImage() throws NotImageRequestException, IOException {
        MultipartFile file = new MockMultipartFile("file", FILENAME, "image/jpeg", "test image content".getBytes());

        doReturn(FILENAME).when(s3ImageService).uploadImage(FOLDER, file);

        String result = s3ImageService.uploadImage(FOLDER, file);

        s3ImageService.deleteImage(result);
    }

    @Test
    @DisplayName("저장된 이미지의 URL을 반환한다.")
    void findImage() throws NotImageRequestException, IOException {
        MultipartFile file = new MockMultipartFile("file", FILENAME, "image/jpeg", "test image content".getBytes());

        doReturn(FILENAME).when(s3ImageService).uploadImage(FOLDER, file);

        String result = s3ImageService.uploadImage(FOLDER, file);

        assertThat("https://test.s3.ap-northeast-2.amazonaws.com" + result).isEqualTo(s3ImageService.getImageUrl(result));
    }
}
