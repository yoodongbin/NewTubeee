
// {
//     title: ''
//     body:''
//     username:''
//     createdAt: new Date()
//     count:0
//     comments: [
//         {
//             username:''
//             createdAt:new Date()
//             comment:''
//         }
//     ]
// }

HTTP.methods({
    'searchPost':function (data) {
        var jsonData=JSON.parse(data);
        postDB.find({$or:
            [{title:new RegExp(jsonData.searchText,'i')},
                {body: new RegExp(jsonData.searchText,'i')},
                {username:new RegExp(jsonData.searchText,'i')}]
        }).fetch();

        return {
            status:' success',
            data: posts
        }
    },

    'removeComment':function (data) {
        var jsonData = JSON.parse(data);
        //요청 사용자가 작성자인지 확인
        var post = postDB.findOne({_id:jsonData._id});

      var comment = post.comments[jsonData.commentIndex];

      if(comment.username !== jsonData.username) {
          return{
              status: '코멘트 작성자만 삭제 가능합니다'
          }
      }
        //요청 댓글을 삭제 한다.
        post.comments.splice(jsonData.commentIndex,1);
        postDB.update({_id:jsonData._id}, post);

        return{
            status:'removeComment success'
        }

    },

    'addComment':function (data) {
    var jsonData = JSON.parse(data);


    var obj = {
        username: jsonData.username,
        createdAt: new Date(),
        comment: jsonData.comment
    }
    var comment = postDB.findOne({_id: jsonData._id});
    comment.comments.push(obj);

    postDB.update({_id: jsonData._id});

    return {
        status: 'addComment success'
    }

    },

    'countUpPost':function (data) {
        var jsonData=JSON.parse(data); //data가져오기
        var post = postDB.findOne({_id:jsonData._id});
        var count = post.count+1;
        postDB.update({_id:jsonData._id},{
            $set:{
                count:count
            }
        });

        return{
            status:'countUpPost success'
        }

    },
    //혹시 제목이나 본문에 내용이 빠져있을 경우가 있기 떄문에 예외처리 해줄 것 이다. //
    'updatePost':function (data) {
        var jsonData=JSON.parse(data);
        //jsonData['title'] 찾아보고 없으면 undefined
        // jsonData.title 찾아보고 있으면 error
        if(jsonData['title']===undefined||
            jsonData['body']===undefined||
            jsonData['_id']===undefined)
        {
                return {
                    status:'필수 항목이 빠져 있습니다'
                }
        }


        // var keys = object.getOwnPropertyNames(jsonData);
        // var title=keys.indexOf('title');
        // var body =keys.indexOf('body');
        // var id = keys.indexOf('_id');

        // if(keys.indexOf('title')=== -1 || keys.indexOf('body') === -1 || keys.indexOf('_id') === -1) {
        //     return {
        //         status:'필수 항목이 빠져 있습니다'
        //     }
        // }

        postDB.update({_id:jsonData._id},{
            $set:{
                title:jsonData.title,
                body:jsonData.body
            }
        });

        return {
            status:'updatePost success'
        }
    },
    //게시글 지우기
    'removePost':function (data) {
        var jsonData = JSON.parse(data);
        postDB.remove({_id:jsonData._id});

        return {
            status:'removePost success'
        }

    },

    //목록의 단 한개?
    'getPost':function (data) {
        var jsonData = JSON.parse(data);

        var post =postDB.findOne({_id:jsonData._id});

        return{
            status:'getPost success',
            data : post
        }
    },
    //목록가져오기
    'getPosts':function (data) {
        var jsonData = JSON.parse(data);
        var posts = postDB.find({}).fetch(); //전체 게시글을 가져오는 ({})를 정제해주는 fetch


        return{
            status:'getPosts success',
            data : posts
        }

    },
    //게시판구현하기
 'insertPost':function (data) {

     var jsonData = JSON.parse(data);
     jsonData.createdAt=new Date();
     jsonData.count=0;
     jsonData.comments=[];
     postDB.insert(jsonData);
     return {
         status:'insertPost success'
     }
 },

    'removeUser':function (data) {
        var jsonData = JSON.parse(data);


      var rslt =userDB.remove({username: jsonData.username});
      if(rslt !== 1) {
          //데이터가 1개가 지워진 상황이 아니면 , 에러
          return {
              status: '데이터 삭제에 문제가 있습니다. 고객센터에 문의바람'
          }
      }

      return {
          status: 'remove success'
      };

    },
    'updateUser':function(data) {
        var jsonData = JSON.parse(data);

       var user = userDB.findOne({
            username: jsonData.username
        });

        if( user === undefined) {
            return {
                status : '회원이 없습니다'
            }
        }
        //방식1
        // user.address=jsonData.address;
        // user.nickname=jsonData.address;
        // user.phone=jsonData.address;
        // userDB.update({username: jsonData.username},{address:jsonData.address});

        //방식2
        // $set: {} {}해당하는 영역에 필드만 바꾸ㅓ랑
        userDB.update({username: jsonData.username},{
            $set: {
                address: jsonData.address,
                phone: jsonData.phone,
                nickname: jsonData.nickname
            }
        });


        return {
            status: 'updateUser success'
        };

    },
    'getPosts':function (data) {
        var jsonData = JSON.parse(data);

        //jsondata예시
        {
            username:''

        }
        // :는 =과 같다, 넘겨주는 의미
        var post = postDB.find(
        {
            username: jsonData.username
        }
        );

        return post.fetch();
        // postDB에 있는 data를 find를 통해 array로 꺼내서 fetch가 형식에 맞게 보여줌

    },
    'postArticle':function (data) {
    var jsonData = JSON.parse(data);

    // jsonData 예시
        {
            username: ''
            title: ''
            body: ''

        }
        postDB.insert(jsonData);

        return{
            status:'success'
        }

    },
    'login': function (data) {
        //javascript code
        var jsonData = JSON.parse(data);

        //username으로 db에서 회원이 있는지 확인 후 있으면 성공, 없으면 error
        //비밀번호가틀렸으면 비밀번호 오류 return

        var check = userDB.findOne(
            {
                username: jsonData.username
            }
        );

        if (check === undefined) {
            //회원이 있음, 가입 x
            return {
                status: 'error'
            }
        }
        console.log(check)
        if (check.password !== jsonData.password) {
            return {
                status: 'error'
            }
        }

        return {
            status: '로그인!'
        }
    },


    'join': function (data) {
        //javascript code
        var jsonData = JSON.parse(data)

        // console.log(jsonData);

        // if (password !== jsonData.username) {
        //     //회원이 있음, 가입 x
        //     return {
        //         status: 'error'
        //     }
        // }
        //
        // return {
        //     status: 'success'
        // }

// 기존회원아이디가있으면 가입 안되는 로직
        //select*from userDB where username = data.username
        //회원ㅇ ㅣ없으면 가입시키자 -> 회원이 있으면 함수를 종료
        //count로 해서 지금 기입한 username과 같은 name이 있는지를 확인해볼 수도있다,
//       find==select 가로안에는 where절
        var obj = userDB.findOne(
            {username: jsonData.username}
        );
        if (obj !== undefined) {
            //회원이 있음, 가입 x
            return {
                status: '기존 회원이 있습니다'
            }
        }

//비밀번호와 비밀번호 확인이 다르면 오타로 생각해서 거절


        if(jsonData.password !== jsonData.passwordConfirm)
        {
            return {
                status : '비밀번호와 확인이 다름'
            }
        }

        //비밀번호 자릿수가 8자리 이상이어야 함
        if(jsonData.password.length<8 ) {
            return {
                status: '비밀번호는  8자리 이상이어야 합니다.'
            }
        }

        if(jsonData.username.length>8 ) {
            return {
                status: '아이디는  8자리 이하이어야 합니다.'
            }
        }

        //정상 가입 후에는 이메일 인증을 통해 인증이 되어야만 정상 회원으로 등급업한다.



   jsonData.createdAt = new Date();

   userDB.insert(jsonData);

    return {
      'login': 'value1',
      'contribution': 'value2'
    }
}
});
