package labletest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/7 3:36 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetectionResult {
    private DetectionData data;
    private int statusCode;

    public static class DetectionData {
        private List<String> cls;
        private List<List<Integer>> xyxy;

        public List<String> getCls() {
            return cls;
        }

        public void setCls(List<String> cls) {
            this.cls = cls;
        }

        public List<List<Integer>> getXyxy() {
            return xyxy;
        }

        public void setXyxy(List<List<Integer>> xyxy) {
            this.xyxy = xyxy;
        }
    }
}
