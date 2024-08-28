import os
from moviepy.editor import VideoFileClip
from PIL import Image

output_folder = "../frames"
video_path = "/Users/lisc/zgxw/workspace/java/myTest/src/main/resources/test.mp4"
frame_rate = 5

# 创建输出文件夹
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

# 使用VideoFileClip加载视频
clip = VideoFileClip(video_path)
fps = clip.fps

# 计算目标帧间隔
target_frame_interval = fps // frame_rate

count = 0
for t in range(int(clip.duration*fps)):
    if t % target_frame_interval == 0:
        frame_path = os.path.join(output_folder, f"frame_{count}.jpg")
        # 保存帧到文件
        frame = clip.get_frame(t/fps)
        frame_image = Image.fromarray(frame)
        frame_image.save(frame_path)
        count += 1

clip.close()