"""
rrrffffrfrfr
frfrfrrf
frfrfffrfrfr
"""
# --------------------------------------------------
# print('hello,world!')
# --------------------------------------------------


# --------------------------------------------------
# import turtle
# turtle.pensize(4)
# turtle.pencolor('red')
# turtle.forward(100)
# turtle.right(90)
# turtle.forward(100)
# turtle.right(90)
# turtle.forward(100)
# turtle.right(90)
# turtle.forward(100)
# turtle.mainloop()
# --------------------------------------------------


# --------------------------------------------------
# import this
# --------------------------------------------------



# --------------------------------------------------
# a = int(input('a = '))
# b = int(input('b = '))
# print('%d + %d = %d' % (a, b, a + b))
# print('%d - %d = %d' % (a, b, a - b))
# print('%d * %d = %d' % (a, b, a * b))
# print('%d / %d = %f' % (a, b, a / b))
# print('%d // %d = %d' % (a, b, a // b))
# print('%d %% %d = %d' % (a, b, a % b))
# print('%d ** %d = %d' % (a, b, a ** b))
# --------------------------------------------------




# --------------------------------------------------
# a = 10
# b = 3
# a += b # 相当于：a = a + b
# a *= a + 2 # 相当于：a = a * (a + 2)
# print(a) # 想想这里会输出什么
# --------------------------------------------------



# --------------------------------------------------
# f = float(input('请输入华氏温度: '))
# c = (f - 32) / 1.8
# print('%.1f华氏度 = %.1f摄氏度' % (f, c))

# f1 = float(input('请输入摄氏温度：'))
# c1 = f1*1.8+32
# print('%.1f摄氏度 = %.1f华氏度' % (f1,c1)) 
# --------------------------------------------------





# --------------------------------------------------
# """
# 正整数的反转

# """

# num = int(input('num = '))
# reversed_num = 0
# while num > 0:
#     reversed_num = reversed_num * 10 + num % 10
#     num //= 10
# print(reversed_num)
# --------------------------------------------------





# --------------------------------------------------
"""
《百钱百鸡》问题

Version: 0.1
Author: 骆昊
"""

# for x in range(0, 20):
#     for y in range(0, 33):
#         z = 100 - x - y
#         if 5 * x + 3 * y + z / 3 == 100:
#             print('公鸡: %d只, 母鸡: %d只, 小鸡: %d只' % (x, y, z))
# --------------------------------------------------




# --------------------------------------------------
"""
Craps赌博游戏
我们设定玩家开始游戏时有1000元的赌注
游戏结束的条件是玩家输光所有的赌注

Version: 0.1
Author: 骆昊
"""
# from random import randint

# money = 1000
# while money > 0:
#     print('你的总资产为:', money)
#     needs_go_on = False
#     while True:
#         debt = int(input('请下注: '))
#         if 0 < debt <= money:
#             break
#     first = randint(1, 6) + randint(1, 6)
#     print('玩家摇出了%d点' % first)
#     if first == 7 or first == 11:
#         print('玩家胜!')
#         money += debt
#     elif first == 2 or first == 3 or first == 12:
#         print('庄家胜!')
#         money -= debt
#     else:
#         needs_go_on = True
#     while needs_go_on:
#         needs_go_on = False
#         current = randint(1, 6) + randint(1, 6)
#         print('玩家摇出了%d点' % current)
#         if current == 7:
#             print('庄家胜')
#             money -= debt
#         elif current == first:
#             print('玩家胜')
#             money += debt
#         else:
#             needs_go_on = True
# print('你破产了, 游戏结束!')
# --------------------------------------------------




# ------------------------？？？？？--------------------------
"""
输出斐波那契数列的前20个数
1 1 2 3 5 8 13 21 ...
Version: 0.1
Author: 骆昊
Date: 2018-03-02
"""

# a = 0
# b = 1
# for _ in range(20):
#     a, b = b, a + b
#     print(a, end=' ')
# --------------------------------------------------




# --------------------------------------------------
# s1 = 'hello, world!'
# s2 = "hello, world!"
# # 以三个双引号或单引号开头的字符串可以折行
# s3 = """
# hello, 
# world!
# """
# print(s1, s2, s3, end='')


# 转义
# s1 = '\'hello, world!\''
# s2 = '\n\\hello, world!\\\n'
# print(s1, s2, end='')


# 进制
# s1 = '\141\142\143\x61\x62\x63'
# s2 = '\u9a86\u660a'
# print(s1, s2)


# 非转义
# s1 = r'\'hello, world!\''
# s2 = r'\n\\hello, world!\\\n'
# print(s1, s2, end='')


# s1 = 'hello ' * 3
# print(s1) # hello hello hello 
# s2 = 'world'
# s1 += s2
# print(s1) # hello hello hello world
# print('ll' in s1) # True
# print('good' in s1) # False
# str2 = 'abc123456'
# # 从字符串中取出指定位置的字符(下标运算)
# print(str2[2]) # c
# # 字符串切片(从指定的开始索引到指定的结束索引)
# print(str2[2:5]) # c12
# print(str2[2:]) # c123456
# print(str2[2::2]) # c246
# print(str2[::2]) # ac246
# print(str2[::-1]) # 654321cba
# print(str2[-3:-1]) # 45



# a, b = 5, 10
# print(f'{a} * {b} = {a * b}')

# --------------------------------------------------

# 集合

# list1 = [1, 3, 5, 7, 100]
# print(list1) # [1, 3, 5, 7, 100]
# # 乘号表示列表元素的重复
# list2 = ['hello'] * 3
# print(list2) # ['hello', 'hello', 'hello']
# # 计算列表长度(元素个数)
# print(len(list1)) # 5
# # 下标(索引)运算
# print(list1[0]) # 1
# print(list1[4]) # 100
# # print(list1[5])  # IndexError: list index out of range
# print(list1[-1]) # 100
# print(list1[-3]) # 5
# list1[2] = 300
# print(list1) # [1, 3, 300, 7, 100]
# # 通过循环用下标遍历列表元素
# for index in range(len(list1)):
#     print(list1[index])
# # 通过for循环遍历列表元素
# for elem in list1:
#     print(elem)
# # 通过enumerate函数处理列表之后再遍历可以同时获得元素索引和值
# for index, elem in enumerate(list1):
#     print(index, elem)




# list1 = [1, 3, 5, 7, 100]
# # 添加元素
# list1.append(200)
# list1.insert(1, 400)
# # 合并两个列表
# # list1.extend([1000, 2000])
# list1 += [1000, 2000]
# print(list1) # [1, 400, 3, 5, 7, 100, 200, 1000, 2000]
# print(len(list1)) # 9
# # 先通过成员运算判断元素是否在列表中，如果存在就删除该元素
# if 3 in list1:
# 	list1.remove(3)
# if 1234 in list1:
#     list1.remove(1234)
# print(list1) # [1, 400, 5, 7, 100, 200, 1000, 2000]
# # 从指定的位置删除元素
# list1.pop(0)
# list1.pop(len(list1) - 1)
# print(list1) # [400, 5, 7, 100, 200, 1000]
# # 清空列表元素
# list1.clear()
# print(list1) # []




# list1 = ['orange', 'apple', 'zoo', 'internationalization', 'blueberry']
# list2 = sorted(list1)
# # sorted函数返回列表排序后的拷贝不会修改传入的列表
# # 函数的设计就应该像sorted函数一样尽可能不产生副作用
# list3 = sorted(list1, reverse=True)
# # 通过key关键字参数指定根据字符串长度进行排序而不是默认的字母表顺序
# list4 = sorted(list1, key=len)
# print(list1)
# print(list2)
# print(list3)
# print(list4)
# # 给列表对象发出排序消息直接在列表对象上进行排序
# list1.sort(reverse=True)
# print(list1)



# 创建集合的字面量语法
# set1 = {1, 2, 3, 3, 3, 2}
# print(set1)
# print('Length =', len(set1))
# # 创建集合的构造器语法(面向对象部分会进行详细讲解)
# set2 = set(range(1, 10))
# set3 = set((1, 2, 3, 3, 2, 1))
# print(set2, set3)
# # 创建集合的推导式语法(推导式也可以用于推导集合)
# set4 = {num for num in range(1, 100) if num % 3 == 0 or num % 5 == 0}
# print(set4)

# set1.add(4)
# set1.add(5)
# set2.update([11, 12])
# set2.discard(5)
# if 4 in set2:
#     set2.remove(4)
# print(set1, set2)
# print(set3.pop())
# print(set3)

# --------------------------------------------------

# 字典

# 创建字典的字面量语法
# scores = {'骆昊': 95, '白元芳': 78, '狄仁杰': 82}
# print(scores)
# # 创建字典的构造器语法
# items1 = dict(one=1, two=2, three=3, four=4)
# # 通过zip函数将两个序列压成字典
# items2 = dict(zip(['a', 'b', 'c'], '123'))
# # 创建字典的推导式语法
# items3 = {num: num ** 2 for num in range(1, 10)}
# print(items1, items2, items3)

# # 通过键可以获取字典中对应的值
# print(scores['骆昊'])
# print(scores['狄仁杰'])
# # 对字典中所有键值对进行遍历
# for key in scores:
#     print(f'{key}: {scores[key]}')
# # 更新字典中的元素
# scores['白元芳'] = 65
# scores['诸葛王朗'] = 71
# scores.update(冷面=67, 方启鹤=85)
# print(scores)
# if '武则天' in scores:
#     print(scores['武则天'])
# print(scores.get('武则天'))
# # get方法也是通过键获取对应的值但是可以设置默认值
# print(scores.get('武则天', 60))
# # 删除字典中的元素
# print(scores.popitem())
# print(scores.popitem())
# print(scores.pop('骆昊', 100))
# # 清空字典
# scores.clear()
# print(scores)

# --------------------------------------------------


# 跑马灯文字
# import os
# import time


# def main():
#     content = '北京欢迎你为你开天辟地…………'
#     while True:
#         # 清理屏幕上的输出
#         os.system('cls')  # os.system('clear')
#         print(content)
#         # 休眠200毫秒
#         time.sleep(0.2)
#         content = content[1:] + content[0]


# if __name__ == '__main__':
#     main()


# --------------------------------------------------

# 随机验证码
# import random

# def generate_code(code_len=4):
#     """
#     生成指定长度的验证码

#     :param code_len: 验证码的长度(默认4个字符)

#     :return: 由大小写英文字母和数字构成的随机验证码
#     """
#     all_chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
#     last_pos = len(all_chars) - 1
#     code = ''
#     for _ in range(code_len):
#         index = random.randint(0, last_pos)
#         code += all_chars[index]
#     return code

# if __name__ == '__main__':
#     print(generate_code())




# --------------------------------------------------

# def main():
#     num = int(input('Number of rows: '))
#     yh = [[]] * num
#     for row in range(len(yh)):
#         yh[row] = [None] * (row + 1)
#         for col in range(len(yh[row])):
#             if col == 0 or col == row:
#                 yh[row][col] = 1
#             else:
#                 yh[row][col] = yh[row - 1][col] + yh[row - 1][col - 1]
#             print(yh[row][col], end='\t')
#         print()


# if __name__ == '__main__':
#     main()

# --------------------------------------------------

"""
《幸运的基督徒》
有15个基督徒和15个非基督徒在海上遇险，为了能让一部分人活下来不得不将其中15个人扔到海里面去，
有个人想了个办法就是大家围成一个圈，由某个人开始从1报数，报到9的人就扔到海里面，他后面的人接着从1开始报数，报到9的人继续扔到海里面，直到扔掉15个人。
由于上帝的保佑，15个基督徒都幸免于难，问这些人最开始是怎么站的，哪些位置是基督徒哪些位置是非基督徒。
"""


# def main():
#     persons = [True] * 30
#     counter, index, number = 0, 0, 0
#     while counter < 15:
#         if persons[index]:
#             number += 1
#             if number == 9:
#                 persons[index] = False
#                 counter += 1
#                 number = 0
#         index += 1
#         index %= 30
#     for person in persons:
#         print('基' if person else '非', end='')


# if __name__ == '__main__':
#     main()


# --------------------------------------------------


# 读取文件
# def main():
#     try:
#         with open('C:\\Users\\liushensuo\\Desktop\\会议.txt', 'r', encoding='GB2312') as f:
#             print(f.read())
#     except FileNotFoundError:
#         print('无法打开指定的文件!')
#     except LookupError:
#         print('指定了未知的编码!')
#     except UnicodeDecodeError:
#         print('读取文件时解码错误!')


# if __name__ == '__main__':
#     main()


# 读取图片
# def main():
#     try:
#         with open('C:\\Users\\liushensuo\\Desktop\\1.jpg', 'rb') as fs1:
#             data = fs1.read()
#             print(type(data))  # <class 'bytes'>
#         with open('2.jpg', 'wb') as fs2:
#             fs2.write(data)
#     except FileNotFoundError as e:
#         print('指定的文件无法打开.')
#     except IOError as e:
#         print('读写文件时出现错误.')
#     print('程序执行结束.')


# if __name__ == '__main__':
#     main()



# --------------------------------------------------


# 读取json文件
# import json

# def main():
#     mydict = {
#         'name': '骆昊',
#         'age': 38,
#         'qq': 957658,
#         'friends': ['王大锤', '白元芳'],
#         'cars': [
#             {'brand': 'BYD', 'max_speed': 180},
#             {'brand': 'Audi', 'max_speed': 280},
#             {'brand': 'Benz', 'max_speed': 320}
#         ]
#     }
#     try:
#         with open('C:\\Users\\liushensuo\\Desktop\\data.json', 'w', encoding='utf-8') as fs:
#             json.dump(mydict, fs)
#     except IOError as e:
#         print(e)
#     print('保存数据完成!')


# if __name__ == '__main__':
#     main()

# --------------------------------------------------



# 发邮件
from smtplib import SMTP
from email.header import Header
from email.mime.text import MIMEText


def main():
    # 请自行修改下面的邮件发送者和接收者
    sender = 'lsssss@163.com'
    receivers = ['575511614@qq.com', '2840670975@qq.com', 'lssssss@163.com']
    message = MIMEText('用Python发送邮件的示例代码.', 'plain', 'utf-8')
    message['From'] = Header('Lss-sender', 'utf-8')
    message['To'] = Header('Lss-receiver', 'utf-8')
    message['Subject'] = Header('示例代码实验邮件', 'utf-8')
    smtper = SMTP('smtp.163.com')
    # 请自行修改下面的登录口令
    smtper.login(sender, 'lss19960103..')
    smtper.sendmail(sender, receivers, message.as_string())
    print('邮件发送完成!')


if __name__ == '__main__':
    main()