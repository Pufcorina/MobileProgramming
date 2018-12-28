let express = require('express');
let router = express.Router();
const pool = require("../database");

router.get('/', function(req, res, next) {
    let username = req.query.username;
    let password = req.query.password;
    let sql1 = 'SELECT * FROM Accounts WHERE username = ? AND password = ?';
    let sql2 = "UPDATE Accounts SET connected = 1 WHERE username = ? AND password = ?";
    pool.query(sql1, [username, password], function (err, result) {
        if (err) throw err;
        if (result.length !== 0)
        {
            pool.query(sql2, [username, password], function (err, result) {
                if (err) throw err;
            });
            console.log(result[0]);
            res.json(result[0]);
        } else {
            let accountFail = {
                "accountId" : -1,
                "connected" : 0,
                "email" : "",
                "password" : "",
                "roleId" : 0,
                "username" : ""
        };
            res.json(accountFail);
        }
    });
});

module.exports = router;
