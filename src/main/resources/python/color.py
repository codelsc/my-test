import cv2
import numpy as np

# 读取图像，这里假设图像已经是灰度图，如果不是，可以使用cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)转换
image_path = '/Users/lisc/Downloads/2.jpg.png'
gray_image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
# 直方图拉伸
# 找到最小和最大灰度值
min_val = np.min(gray_image)
max_val = np.max(gray_image)
# 拉伸到整个灰度范围（0-255）
stretched_image = cv2.normalize(gray_image, None, alpha=0, beta=255, norm_type=cv2.NORM_MINMAX, dtype=cv2.CV_8U)

# 应用伪彩色映射
pseudo_color_image = cv2.applyColorMap(stretched_image, cv2.COLORMAP_COOL)  # 你可以尝试不同的颜色映射

# 显示结果
cv2.imshow('Original Grayscale Image', gray_image)
cv2.imshow('Stretched and Pseudo Color Image', pseudo_color_image)
cv2.waitKey(0)
cv2.destroyAllWindows()

# 如果需要，可以保存伪彩色图像
# cv2.imwrite('/Users/lisc/Downloads/maoyan_latest_163/4c.png', pseudocolor_image)