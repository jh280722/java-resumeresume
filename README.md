# Resume Resume

## 기능 요구사항
### Item
- item은 text, textArea, date, image, period
  등을 표현할 수 있는 데이터이다.
- item을 사용자가 생성, 수정, 삭제가 가능해야한다.
- item의 명칭 부분을 더블 클릭하여 명칭 수정이 가능하다. 
  ex) 이름 :, 나이 :
- 복사 붙여넣기가 가능해야한다.
- editor 구현 
  - item의 value의 String 값을 변경하여 처리한다.
- 크기 변경 가능
- item, table 사이의 순서 구별이 가능해야한다.

### table
- tableItem의 집합인 표이다.
- 현재 row, col를 저장하고 있다.
- item, table 사이의 순서 구별이 가능해야한다.
#### tableItem
- row, col 위치에 해당되는 value이다.

### Box
- item와 table의 집합으로 하나의 상자이다.
  - box의 이름은 없을 수도 있다.
- box를 추가, 삭제할 수 있어야한다.
- box에 item을 추가하는 버튼이 있어야한다.
- 복사 붙여넣기가 가능해야한다.
- 크기 변경 가능

### Default Document
- user정보에 따라 sortation에 해당되는 기본 틀을 생성해둔다.
- 새 document를 만들때 default document로 등록할 수 있다.

### Document
- box의 집합으로 하나의 문서이다.
    - sortation 내 고유한 이름을 가진다.
      - 생성시 중복 검사를 하여 자동으로 
        변경된 이름으로 추가할 것인지 물어본다.
    - content 공간을 의미한다.
    - 분할 여부 고려 (미정)
- drag & drop으로 sortation끼리의 이동이 가능하다.    
    - 다른 sortation으로 이동시 이름 중복 검사를 한다.
- 복사 붙여넣기가 가능해야한다.
- 새 문서를 만들때 default document로 만들거나 빈 문서로 만들 수 있다.

### Sortation
- document의 집합으로 하나의 구분이다.
    - 인적사항, 경력사항, 자격증 등 고유한 이름을 가진다.
    - 생성, 수정, 삭제가 불가능하다.
- 네비게이터로 어느 위치든 볼 수 있다.

### User
- 로그인, 로그아웃을 할 수 있다.
- 마이페이지를 가진다.
    - 각 sortation에 해당되는 default document를 등록할 수 있다.
    - 회원정보를 수정, 탈퇴할 수 있다.
    

