import os
from docx2pdf import convert

# 指定源文件和目标文件的路径
source_path = '/Users/lisc/Downloads/黑马程序员+linux从入门到精通配套笔记.docx'  # 替换为你的Word文档路径
target_path = '/Users/lisc/Downloads/黑马程序员+linux从入门到精通配套笔记.pdf'  # 替换为你想要的PDF文件路径

# 确保目标文件所在的目录存在（如果需要的话）
# 注意：这里只创建目录，不创建文件
directory = os.path.dirname(target_path)
if not os.path.exists(directory):
    os.makedirs(directory)

# 将Word文档转换为PDF
try:
    convert(source_path, target_path)
    print(f"已转换: {source_path} -> {target_path}")
except Exception as e:
    print(f"转换失败: {e}")