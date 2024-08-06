import os

def create_gitkeep_in_empty_folders(directory):
    for root, dirs, files in os.walk(directory):
        if not dirs and not files:  # Check if the directory is empty
            gitkeep_path = os.path.join(root, '.gitkeep')
            with open(gitkeep_path, 'w') as f:
                pass  # Create an empty .gitkeep file
            print(f'Created .gitkeep in: {root}')
        else:
            for dir in dirs:
                dir_path = os.path.join(root, dir)
                if not os.listdir(dir_path):  # Check if the subdirectory is empty
                    gitkeep_path = os.path.join(dir_path, '.gitkeep')
                    with open(gitkeep_path, 'w') as f:
                        pass  # Create an empty .gitkeep file
                    print(f'Created .gitkeep in: {dir_path}')


current_directory = os.getcwd()

create_gitkeep_in_empty_folders(current_directory)
