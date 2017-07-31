// //사용자중에 admin이 없으면 만들어줘라
var user = userDB.findOne({username: 'admin'});

if(user===undefined) {
    userDB.insert({
        username:'admin',
        nickname: '관리자',
        address:'Korea'
    })
}
//
// //temp는 커서와 같다.
var temp = testDB.find({index:9999, boolean: true});
console.log(temp.fetch());
//
//
// //for
// for (var i=0; i<10000; i++){
//    console.log( testDB.insert({
//         index: i,
//         name: 'test data',
//         boolean:true
//     }));
// }
