import Mock from 'mockjs'

// Mock.mock('http://localhost:9000/getTables', {
//     "code": 200,
//     "msg": null,
//     "data": {
//         "connParam": {
//             "dbName": "kitty",
//             "dbType": "MYSQL",
//             "host": "localhost",
//             "password": "123456",
//             "port": 3306,
//             "userName": "root"
//         },
//         "tableModels": [
//             {
//                 "name": "sys_menu",
//                 "description": "菜单管理",
//                 "tablespace": null,
//                 "columns": []
//             },
//             {
//                 "name": "sys_user",
//                 "description": "用户",
//                 "tablespace": null,
//                 "columns": []
//             }
//         ]
//     }    
//   })