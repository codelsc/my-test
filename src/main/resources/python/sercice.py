from flask import Flask, request, jsonify
import os
import time

app = Flask(__name__)

# 获取环境变量OUTPUT_BASE_PATH
# OUTPUT_BASE_PATH = os.getenv("OUTPUT_BASE_PATH", "/default/path")
OUTPUT_BASE_PATH = '/Users/lisc/zgxw'

# 确保输出路径存在
# if not os.path.exists(OUTPUT_BASE_PATH):
#     os.makedirs(OUTPUT_BASE_PATH)

@app.route('/api/v1/health', methods=['GET'])
def health_check():
    return jsonify({
        "code": 200,
        "message": "Healthy"
    }), 200

@app.route('/api/v1/algorithm/execute', methods=['POST'])
def execute_algorithm():
    # 检查请求中是否有文件
    if 'file' not in request.files:
        return jsonify({
            "code": 400,
            "message": "No file part"
        }), 400

    file = request.files['file']

    # 检查文件是否存在
    if file.filename == '':
        return jsonify({
            "code": 400,
            "message": "No selected file"
        }), 400

    # 保存文件以备压缩
    timestamp = int(time.time())
    file_path = os.path.join(OUTPUT_BASE_PATH, file.filename)
    file.save(file_path)

    # 设置压缩文件的路径
    output_filepath = os.path.join(OUTPUT_BASE_PATH, f"{timestamp}.zip")

    # 压缩文件
    with zipfile.ZipFile(output_filepath, 'w') as zipf:
        zipf.write(file_path, arcname=file.filename)  # arcname 用于在ZIP文件中使用原始文件名

    return jsonify({
        "code": 200,
        "message": "success",
        "data": output_filepath
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8990)
