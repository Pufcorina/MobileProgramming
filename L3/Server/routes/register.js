let express = require('express');
let router = express.Router();
const pool = require("../database");

router.post('/', function(req, res, next) {
    let sql = "INSERT INTO Accounts (username, email, password, roleId) VALUES ?";
    let account = [[
        req.body.username,
        req.body.email,
        req.body.password,
        '1'
    ]];
    pool.query(sql, [account], function (err, result) {
        if (err)
            res.json(false);
        else
            res.json(true)

    })
});

module.exports = router;
