import os
import sys
import cv2

print('aaaaaaaa')
# output_folder = sys.argv[1]
output_folder = '../frames'
# video_path = sys.argv[2]
video_path = '/Users/lisc/zgxw/workspace/java/myTest/src/main/resources/test.mp4'
# frame_rate = int(sys.argv[3])
frame_rate = 5

# 创建输出文件夹
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

# 打开视频文件
vidcap = cv2.VideoCapture(video_path)

# 获取视频的帧率
# video_frame_rate = vidcap.get(cv2.CAP_PROP_FPS)
# 计算间隔帧数
frame_interval = int(video_frame_rate / frame_rate)
success, image = vidcap.read()
count = 0
frame_count = 0

while success:
    print(frame_count)
    if frame_count % frame_interval == 0:
        frame_path = os.path.join(output_folder, f"frame_{count}.jpg")
        cv2.imwrite(frame_path, image)  # 保存帧到文件
        count += 1

    success, image = vidcap.read()
    frame_count += 1

vidcap.release()