let express = require('express');
let router = express.Router();
const pool = require("../database");


router.get('/', function(req, res, next) {
    let email = req.query.email;

    let sql = "UPDATE Accounts SET connected = 0 WHERE email = ?";
    pool.query(sql, [email], function (err, result) {
        if (err)
            res.json(false);
        else
            res.json(result.changedRows)
    });
});

module.exports = router;
