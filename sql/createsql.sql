--用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `uid`         int(11) NOT NULL AUTO_INCREMENT,
    `username`    varchar(255) NOT NULL,
    `nickname`    varchar(255)                                           DEFAULT NULL,
    `password`    varchar(255) NOT NULL,
    `user_role`   varchar(20)  NOT NULL,
    `email`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `delete_flag` tinyint(1) DEFAULT '0',
    `remark`      text CHARACTER SET utf8 COLLATE utf8_general_ci,
    `enable`      tinyint(1) DEFAULT '0',
    `create_time` datetime                                               DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                               DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;


-- 题库 发布的一套一套题
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL,
    `delete_flag` tinyint(1) DEFAULT '0',
    `remark`      text CHARACTER SET utf8 COLLATE utf8_general_ci,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
--题库与题目中间表
DROP TABLE IF EXISTS `question_bank`;
CREATE TABLE `question_bank`
(
    `id`  int(11) NOT NULL AUTO_INCREMENT,
    `bid` int(11) NOT NULL COMMENT '题库id',
    `qid` int(11) NOT NULL COMMENT '题目id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- 题目
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `question_stem`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '棰樺共',
    `type`           tinyint(2) NOT NULL COMMENT '题目类型',
    `options`        text COMMENT '选项',
    `answer`         text COMMENT '答案',
    `explanation`    text COMMENT '答案解析',
    `question_score` decimal(5, 2) DEFAULT NULL,
    `create_time`    datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime      DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


--用户练习一套题库的记录（分为练习和考试两个类型）
DROP TABLE IF EXISTS `user_bank`;
CREATE TABLE `user_bank`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `user_id`     int(11) NOT NULL,
    `type`        tinyint(2) NOT NULL COMMENT '璁板綍绫诲瀷',
    `bank_id`     int(11) NOT NULL,
    `submit`      tinyint(1) DEFAULT '0' COMMENT '是否提交',
    `score`       decimal(5, 2) DEFAULT '0.00' COMMENT '鍒嗘暟',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime      DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--练习记录
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `user_bank_id` int(11) NOT NULL,
    `question_id`  int(11) NOT NULL,
    `user_answer`  text,
    `score`        decimal(5, 2) DEFAULT '0.00',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES (1, 'root', NULL, 'fc9e0c9df7766f260c92bd0371f1b14d', 'admin', '123@qq.com', 0, 'cehis', 0, '2023-7-3 14:11:14', '2023-7-3 14:11:14');
INSERT INTO `user` VALUES (2, 'JIZ', '毛思淼', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 1, 'ceshi', 0, '2022-10-25 11:40:28', '2022-2-11 05:48:30');
INSERT INTO `user` VALUES (3, 'VPyKB', '邵昊然', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 1, 'ceshi', 0, '2022-11-17 02:59:36', '2022-4-24 22:52:13');
INSERT INTO `user` VALUES (4, 'CVqMG', '龙志强222', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-9-7 19:56:16', '2023-7-3 17:34:29');
INSERT INTO `user` VALUES (5, 'Nv3N', '潘嘉懿', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-12-6 15:34:07', '2023-7-3 17:32:35');
INSERT INTO `user` VALUES (6, 'EsqQ', '周嘉熙', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-3-16 23:57:40', '2022-6-21 07:49:55');
INSERT INTO `user` VALUES (7, 'tC8T', '魏绍辉', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-9-15 03:10:55', '2022-5-22 02:15:13');
INSERT INTO `user` VALUES (8, 'ajV1', '罗绍齐', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-7-23 22:43:23', '2022-4-13 23:26:09');
INSERT INTO `user` VALUES (9, 'I9E', '姚绍辉', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-10-4 01:07:28', '2022-8-11 12:18:38');
INSERT INTO `user` VALUES (10, 'TJnuf', '苏烨磊', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-4-21 21:33:02', '2022-9-23 21:33:48');
INSERT INTO `user` VALUES (11, 'yMqh', '赖鸿煊', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-2-4 12:57:47', '2022-8-12 08:24:09');
INSERT INTO `user` VALUES (12, 'pWe', '蒋晓博', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-2-9 23:10:13', '2022-4-27 12:11:32');
INSERT INTO `user` VALUES (13, 'd4uVs', '余建辉', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-12-10 09:29:00', '2022-2-27 10:25:40');
INSERT INTO `user` VALUES (14, '4nzQI', '熊琪', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-11-19 02:51:42', '2022-8-8 02:50:42');
INSERT INTO `user` VALUES (15, '0B', '袁语堂', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-1-3 18:54:55', '2022-8-7 17:44:29');
INSERT INTO `user` VALUES (16, 'ic', '黄明哲', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-2-17 10:34:01', '2022-11-25 22:35:55');
INSERT INTO `user` VALUES (17, 'ldY', '郝健柏', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-8-25 17:14:45', '2022-6-30 02:52:03');
INSERT INTO `user` VALUES (18, 'NepO', '万伟宸', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshiceshi', 0, '2022-8-22 09:06:15', '2022-12-7 13:23:57');
INSERT INTO `user` VALUES (19, 'OMPRm', '严天磊', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'v', 0, '2022-10-19 22:02:05', '2022-10-27 17:57:18');
INSERT INTO `user` VALUES (20, 'xB', '郭志泽', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-12-13 06:28:22', '2022-7-11 06:43:30');
INSERT INTO `user` VALUES (21, 'NF', '陆鑫鹏', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '123@qq.com', 0, 'ceshi', 0, '2022-5-16 09:52:18', '2022-11-8 08:21:18');
INSERT INTO `user` VALUES (22, 'lisi', NULL, '546450ea8735b29681afed83e769d7db', 'user', '123@qq.com', 0, 'ceshi', 0, '2023-7-3 17:00:54', '2023-7-3 17:00:54');
INSERT INTO `user` VALUES (23, 'zhangsan', 'zs', '546450ea8735b29681afed83e769d7db', 'user', NULL, 0, 'ceshi', 0, '2023-7-3 17:15:07', '2023-7-3 17:15:07');

INSERT INTO `question` VALUES (1, '<p data-we-empty-p=\"\">aASDASD</p>', 4, '', '<p data-we-empty-p=\"\">ASASDASD</p>', '<p data-we-empty-p=\"\">ASASDA</p>', 0.00, '2023-7-4 16:01:08', '2023-7-4 16:01:08');
INSERT INTO `question` VALUES (2, '<p>使用卷积神经网络（CNN）对图片分类</p><p>题目描述：你需要设计一个卷积神经网络（CNN）模型，用于对图像进行分类。给定一组图像数据集，包含不同种类的图像（例如猫、狗和汽车）。你需要使用训练数据来训练模型，并使用测试数据对其进行评估。</p><p>要求：</p><ol><li>使用Python和一个合适的深度学习框架（如TensorFlow或PyTorch）来实现模型。</li><li>使用至少两层卷积层和一层全连接层构建模型。</li><li>使用合适的激活函数（如ReLU）和池化层（如最大池化或平均池化）。</li><li>通过交叉熵损失函数和优化算法（如随机梯度下降或Adam）来训练模型。</li><li>使用测试数据对模型进行评估，并计算准确率或其他适当的指标。</li></ol><p>提示：</p><ul><li>首先，你需要准备一个包含标记好的图像数据集。可以使用公共数据集（如MNIST，CIFAR-10或Imagenet），或自己准备数据集。</li><li>在设计卷积神经网络时，可以选择不同的网络结构，例如LeNet-5、VGG16或ResNet等。</li><li>在训练模型时，需要将图像数据进行预处理，例如归一化和数据增强，以提高模型的性能和鲁棒性。</li><li>在模型训练过程中，可以使用早期停止策略或学习率衰减等技巧来改善模型的训练效果。</li><li>可以使用验证集来调整模型的超参数，例如卷积核大小、滤波器个数、全连接层的神经元个数等。</li></ul><p>请据题目要求设计并实现卷积神经网络模型，并使用合适的数据集进行训练和测试。祝你好运！</p>', 0, '', '<p data-we-empty-p=\"\">略</p>', '<p data-we-empty-p=\"\">略<br></p>', 20.00, '2023-7-4 16:01:40', '2023-7-4 16:01:40');
