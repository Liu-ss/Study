# Git操作

1、查看分支：`git branch --all`【`git branch -a`】    （退出：显示END时按Q）
2、刷新分支列表：`git remote update origin --prune`
3、删除本地分支：`git branch -d` 分支名（remotes/origin/分支名）
4、强制删本地：`git branch -D 分支名`
5、删除远程分支：`git push origin --delete 分支名`（remotes/origin/分支名
6、推送本地分支至远程分支：`git push origin 本地分支:远程分支`
7、删除远程分支有两种方法：①推送一个空分支至要删除的远程分支：`git push origin  :要删除的分支`
													 ②使用删除：`git push origin --delete 远程分支名`

