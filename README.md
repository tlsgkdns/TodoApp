<h1>To Do App Step4</h1>

<h2>Use Case Diagram</h2>
<img src="https://github.com/tlsgkdns/TodoApp/assets/24753709/4852588b-8275-43d3-b660-7a7c65a00e59">
<h2>ERD</h2>
<img src="https://github.com/tlsgkdns/todoApp/assets/24753709/38f0f804-15fe-4251-815a-d332bdcea12f">
<h2>API 명세서</h2>
<img src="https://github.com/tlsgkdns/todoApp/assets/24753709/e0621800-fdee-471c-b219-577c26575893">
<h2>개념 정리</h2>
<h3>Offset Paging vs Cursor Paging</h3>
<h4>Offset Paging</h4>
Offset Paging은 일반적인 방식의 쿼리를 사용한다.
클라이언트가 "현재 페이지의 번호"와 "페이지 당 요청하는 자료의 갯수"를 서버에 파라미터로 요청하면, 
서버에서는 그 값을 활용해서 오프셋 값을 구해서 페이징한 데이터 값을 클라이언트에게 전달한다.<br>
`Offset = (현재 페이지의 번호 - 1) * 페이지 당 요청하는 자료의 갯수`<br>
SQL 쿼리로 표현하면 다음과 같이 표현할 수 있다.<br>
```
SELECT * FROM {테이블 이름} LIMIT {페이지 당 자료 갯수} OFFSET {오프셋}
SELECT * FROM board LIMIT 20 OFFSET 60; // 61~80 까지
SELECT * FROM board LIMIT 20 OFFSET 80; // 81~100 까지
SELECT * FROM board LIMIT 30 OFFSET 50; // 51~80 까지
```
이 방식의 장점은 쿼리가 복잡하지 않고, 쉽게 구현할 수 있다는 점이다.<br>
반면에 단점은 OFFSET이 크면 클 수록 비효율적이란 점이다.<br> 
데이터베이스에서 이 쿼리를 사용할 때,이전의 데이터를 모두 조회하고,잘라내는 방식이기에 OFFSET에 정비례하게 시간이 지연된다.<br>
또 다른 단점은 데이터가 누락이 되거나 중복이 될 수 있다. 예를 들어서 사용자가 새 데이터가 추가된 사이에, 1페이지에서 2페이지로 넘어갔을 경우, 사용자는 1페이지의 내용을 2페이지에서도 보게된다.
그래서 실시간으로 데이터가 추가되거나 삭제되는 환경에서 사용하기 부적절하다.

<h4>Cursor Paging</h4>
Cursor Paging은 WHERE 절을 활용해서 Paging을 하는 방식이다.
SQL은 다음과 같다.
```
SELECT * FROM {테이블 이름} WHERE id > {기준값} LIMIT {페이지 당 자료 갯수}
SELECT * FROM board WHERE id > 60 LIMIT 20; // 61~80 까지
SELECT * FROM board WHERE id > 80 LIMIT 20; // 81~100 까지
SELECT * FROM board WHERE id > 50 LIMIT 30; // 51~80 까지
```
Offset Paging이 우리가 원하는 데이터가 몇 번째에 있다는 데에 집중한다면, Cursor Paging은 우리가 원하는 데이터가 어떤 데이터의 다음에 있다에 집중한다.

이 방식의 장점은 Offset에 비해 데이터를 더욱 효율적으로 조회할 수 있는 점이다. <br>
또 실시간으로 데이터가 추가 되거나 삭제되어도 누락되거나 중복되지 않는다.
반면에 단점은 정렬 방식이 제한적이란 점이다. 정렬할 컬럼에 중복된 값이 존재해선 안된다.<br>
만일 중복된 값이 존재하는 방식으로 정렬을 사용하면 데이터가 누락 될 수도 있다. <br>
따라서 중복된 값을 정렬 기준으로 세울 경우 고유한 값과 함께 정렬 기준을 세운다.
```
SELECT * 
FROM product p 
WHERE p.price > 3000 or (p.price = 3000 and id > 30)
ORDER BY p.price DESC p.id ASC
LIMIT 10
```
따라서 Offset에 비해 구현이 복잡하다.

결론적으로 데이터를 효율적으로 다룰 수 있는 Cursor Paging의 사용이 많이 추천된다.<br>
하지만, 이것도 쿼리에 따라 Offset보다 더 비효율적일 수도 있으므로, 상황에 맞게 구현하는 것이 더 좋다.

<h3> N + 1 Query 문제 </h3>
<h4>개념</h4>
JPA를 사용하다 발생하는 문제로써, 의도치 않게 여러 번의 select 문을 발생하는 현상을 의미한다. <br>
정확히는 N+1이 아닌 1(엔티티 조회 쿼리)+N(추가 쿼리)으로 불리우는게 이해가 더 쉽다.<br>
이 문제는 하위 Entity가 있을 때, Fetch 타입이 Eager일 때와 Lazy인 경우 연관 테이블에 접근할 때 발생한다.<br><br>
<h4>해결 방법</h4>
<h5>Fetch Join</h5>
N+1 문제가 발생하는 이유는 한 쪽 테이블만 조회하고 다른 테이블은 따로 조회하기 때문이다. 따라서 미리 두 테이블을 Join하는 방식을 사용할 수 있다.
즉, 미리 JPQL을 작성해서 사용하는 방식이 있다.

```
  @Query("select b from Board b join fetch b.comments")
  List<Team> findAllJoinFetch();
```

이 방식의 단점은 JPA가 제공하는 Pageable 기능을 사용할 수 없고, 번거롭게 쿼리를 작성해한다는 점이다.
<h5>EntityGraph 어노테이션</h5>
@EntityGraph의 attributesPaths에 같이 조회할 연관 엔티티명을 적어서 N+1 문제를 방지할 수 있다.
이 문제의 단점은 Outer Join을 기본으로 활용해서 성능 최적화에 불리하다는 것이다.

```
@EntityGraph(attributePaths = {"comment"})
List<User> findAllEntityGraph();
```
<h5>결론</h5>
이 두 해결방안 모두 중복된 데이터가 발생할 수 있다는 문제점이 있다. <br>
그래서 중복방지를 위해 Distinct를 추가로 쓰거나 Set으로 데이터를 저장하는 방안이 있다.<br>
추가적으로 지정된 size만큼 SQL의 IN절을 사용하는 조회하는 BatchSize 어노테이션을 사용해서 N+1 문제를 해결할 수도 있다.

<h3> Basic Authentication VS Bearer Authentication</h3>
<h4>Basic Authentication</h4>
Basic Authentication은 base64로 인코딩한 사용자ID와 비밀번로로 만든 문자열을 Header에 입력해서 서버에게 인증하는 방식이다.
이 방식의 최대의 장점은 간단함이다. 사용자ID와 비밀번호 외의 별도의 인증 정보를 요구하지 않아서, 매우 간편하다.<br>
하지만 이 방식을 사용하면 서버가 사용자 목록을 저장을 하는데, 사용자가 많으면 권한을 확인하는 데 시간이 걸려서, 서버에 부담이 커진다.<br>
거기다 사용자 권한을 정교하게 제어할 수 없다. 그래서 세세한 권한설정을 위해선 추가적인 구현이 필요하다.
<h4>Bearer Authentication</h4>
Beaer Authentication은 Bearer Token을 사용하는 OAuth 프레임워크 방식이다.<br>
Client가 리소스 소유자에게 인증을 허가 받으면 인증서버에서 액세스 토큰을 할당받어서 이 토큰을 사용해 리소스 서버에서 보호된 데이터를 받는 형식이 OAuth 프레임워크이다. 
Bearer Token은 16진수의 문자열이다. JSON Web Token(이하 JWT)를 사용하기도 한다.<br>
이 Token은 클라이언트에서 해석을 할 수 없고, 사용자의 정보를 담지 않는 대신 서버에서 클라이언트의 권한을 확인 할 수 있는 데이터를 충분히 복잡한 알고리즘을 활용해 인코딩해야한다.<br>
이 인증방식은 확장이 쉽고, 안전하며, 정교하게 권한을 제어할 수 있다는 장점이 있다.<br>
