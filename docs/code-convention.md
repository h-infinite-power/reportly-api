# 패키지 구조
- table name으로 디렉터리가 분리되고 controller, service, repository, entity, dto 등은 각각의 패키지로 분리되어 있음
- ex) # TODO : example을 com.human.infinite.power.domain으로 변경 필요  
  - com.example.testattendances.controller
  - com.example.testattendances.service
  - com.example.testattendances.repository
  - com.example.testattendances.entity
  - com.example.testattendances.dto
- 각 패키지 내에서는 관련된 클래스들끼리 묶어서 관리함

# DB
## JPA
- Repository의 경우 JpaRepository를 상속받아 사용하며 간단한 경우 이름메서드로 처리함
  - join이 들어가는 경우 이름 메서드를 사용하지 않고 @Query 이용하여 JPQL로 작성함 
  - Controller로 entity가 넘어가서는 안되며 필요한 경우에는 DomainResponseDto를 만들어서 반환
- entity 클래스는 @Entity 어노테이션을 사용하여 정의하며 테이블 이름은 TableName 어노테이션으로 지정함
  - 모든 table row 생성시에 id는 시계열에 따른 id를 얻어내는 전역 static 메서드를 통해 생성한다.(중복이 최대한 되지 않도록, 시계열 생성이므로 아이디의 순차적인 오름차순은 유지됨)
    ``` java
      public class IdGenerator {
            private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            private static final AtomicInteger sequence = new AtomicInteger(0);
      
            private static final long UTC_EPOCH_2020 = 1577836800000L;
            private static final int SHIFT_BIT = 23;
            private static final int BIT_23_VALUE = 0x800000;
      
            public static Long generateId() {
                long sub = System.currentTimeMillis() - UTC_EPOCH_2020;
                return (sub << SHIFT_BIT) + new SecureRandom().nextInt(BIT_23_VALUE);
            }
      }
    ``
- 생성하는 모든 메서드에는 자바독을 만들고, 메서드의 길이는 15~20줄로 제한한다.
- 계산이 복잡한 내용이 있을 경우 매 줄마다 주석을 달아서 이해하기 쉽게 한다.
- jpaRepository 등 database에서 result를 조회해오는 경우 반드시 DTO 클래스를 생성하여 해당 클래스로 필요한 필드/로우들을 조회한다.
- 복잡한 쿼리의 경우  jpql로 join fetch 하여 entity를 반환한다. 이후 entity는 서비스단에서 DTO class로 변환하여 controller로 반환한다. 
- DTO로 생성할때는 내부 클래스 형태로 만들지 말고 개별적으로 각각 생성할 것.
- DTO에서 postfix가 No로 끝나는 것은 반환 시 Long에서 String, 요청 시 String을 Long으로 받을 수 있도록 처리해줘.
ex) @JsonSerialize(using = ToStringSerializer.class)
- querydsl은 절대 사용하지 않는다.

# swagger
- 사용할시에는 실제 메서드 내에 파라미터를 지저분하게 하지 않고 메서드 상단에 @Operation @Parameters 등의 어노테이션을 붙여서 사용한다.
- swagger-ui example값은 중복을 피하기위해 SwaggerParameter.java를 정의하고 상수를 가져와서 사용한다.
- swagger-ui 를 구성하기 위해 @Controller에 설명을 작성할때는 너무 더러워지지 않게 class 상단에 @Tag 어노테이션을 사용하고 각 api 메서드 위에 @Operation과 @Parameters 어노테이션만 사용하여 나타낸다.

# 예외처리
- UserException.java가 없다면 만들고 있다면 해당 Exception을 통해 400 에러를 통제하라
- RuntimeExcetion 같은 경우에는 500으로 반환하고 서버에서 예상치 못한 에라가 발생했다고 해라 그리고 그 아래에 실제 버그의 stackTrace를 남겨라(차후 운영에서는 남겨지지 않도록 처리)