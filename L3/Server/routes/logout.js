let express = require('express');
let router = express.Router();
const pool = require("../database");


router.get('/', function(req, res, next) {
    let sql = "UPDATE Accounts SET connected = 0 WHERE username = ? AND password = ?";
    pool.query(sql, [req.params.username, req.params.password], function (err, result) {
        if (err)
            res.json(false);
        else
            res.json(true)
    });
});

module.exports = router;
