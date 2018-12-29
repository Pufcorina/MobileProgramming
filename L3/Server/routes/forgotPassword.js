let express = require('express');
let router = express.Router();
const pool = require("../database");

router.get('/', function(req, res, next) {
    let email = req.query.email;
    let sql = 'SELECT password FROM Accounts WHERE email = ?';
    pool.query(sql, [email], function (err, result) {
        if (err) throw err;
        if (result.length !== 0)
        {
            res.json(result[0].password);
        } else {
            res.json("error");
        }
    });
});


module.exports = router;
