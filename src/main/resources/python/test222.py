import cv2

cap = cv2.VideoCapture('https://minio-api.csnd.com/ai-flow-dataset-img-test/NzgzX3Rlc3RiX3Rlc3RiVjEuMC4x_dGVzdGI=_M2IxYi01LW5leHR0b2tlbl/lia/mnKw=.mp4')

frame_count = 0
while True:
    # 读取视频帧
    ret, frame = cap.read()
    if not ret:
        break

    # 计数器累加
    frame_count += 1

print('视频总帧数：', frame_count)

# 释放资源
cap.release()