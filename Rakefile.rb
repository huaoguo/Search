# encoding: utf-8

# 备份文件夹的路径
DIR = "E:/backup"
# 当前数据库的文件路径
DB_PATH = "db/development.db"

desc "show all backup files"
task :list do
  puts Dir.glob("#{DIR}/*.db")
end

desc "restore db"
task :restore,:version do |t,args|
  version = args.version
  path = "#{DIR}/development-#{version}.db"
  if(File.exists?(path))
    FileUtils.cp path,DB_PATH
    puts "finished"
  else
    puts "invalid version"
  end
end

desc "backup db"
task :backup do
  if(File.exists?(DB_PATH))
    v1 = Time.now.strftime('%m%d')
    paths = Dir.glob("#{DIR}/development-#{v1}*.db")
    v2 = paths.length + 1
    FileUtils.cp DB_PATH,"#{DIR}/development-#{v1}-#{v2}.db"
    puts "finished"
  else
    puts "db not exists"
  end
end
