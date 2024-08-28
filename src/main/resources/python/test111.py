import cv2

def get_video_frame_count(video_path):
    cap = cv2.VideoCapture(video_path)

    # 获取视频文件的总帧数
    total_frames = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    return total_frames

# video_path = "/Users/lisc/zgxw/workspace/java/myTest/src/main/resources/test.mp4"
video_path = "https://minio-api.csnd.com/ai-flow-dataset-img-test/NzgzX3Rlc3RiX3Rlc3RiVjEuMC4x_dGVzdGI=_M2IxYi01LW5leHR0b2tlbl/lia/mnKw=.mp4"
frame_count = get_video_frame_count(video_path)
print("Total frame count: ", frame_count)