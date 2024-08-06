import os

def remove_gitkeep_files(directory):
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file == '.gitkeep':
                gitkeep_path = os.path.join(root, file)
                os.remove(gitkeep_path)
                print(f'Removed .gitkeep from: {gitkeep_path}')

# Get the current working directory
current_directory = os.getcwd()

# Run the function using the current directory as the root
remove_gitkeep_files(current_directory)
