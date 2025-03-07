package project.springmarket.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.springmarket.model.member.Member;
import project.springmarket.repository.MemberMybatisRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberMybatisRepository memberMybatisRepository;

    Member testMember;

    @Before
    public void init(){
        testMember = getTestMapper();
        memberMybatisRepository.insertMember(testMember);
    }

    // given when then
    @Test
    public void findByIdTest(){
        Member findByIdMember = memberMybatisRepository.findById(testMember.getMemberLoginID());

        assertThat(findByIdMember.getMemberLoginID()).isEqualTo(testMember.getMemberLoginID());
        assertThat(findByIdMember.getMemberLoginPW()).isEqualTo(testMember.getMemberLoginPW());
        assertThat(findByIdMember.getAddressCodeNo()).isEqualTo(testMember.getAddressCodeNo());
    }

     //addressCode만 바꾸는 경우, PW는 NULL
    @Test
    public void updateMemberAddressTest(){
        int updateAddressCode = 500;
        Member updateOnlyAddress = getTestMapper();
        updateOnlyAddress.setAddressCodeNo(updateAddressCode);
        memberMybatisRepository.updateMember(updateOnlyAddress);

        Member updateMember = memberMybatisRepository.findById(testMember.getMemberLoginID());
        assertThat(updateMember.getAddressCodeNo()).isEqualTo(updateAddressCode);
    }

    // PW만 바꾸는 경우, addressCode는 0
    @Test
    public void updateMemberPWTest(){
        String updatePW = "updatePW";
        Member updateOnlyPW = getTestMapper();
        updateOnlyPW.setMemberLoginPW(updatePW);
        memberMybatisRepository.updateMember(updateOnlyPW);

        Member updateMember = memberMybatisRepository.findById(testMember.getMemberLoginID());
        assertThat(updateMember.getMemberLoginPW()).isEqualTo(updatePW);
    }

    // addressCode, PW 모두 바꾸는 경우
    @Test
    public void updateMemberTest(){
        int updateAddressCode = 500;
        String updatePW = "updatePW";
        Member updateAll = getTestMapper();
        updateAll.setAddressCodeNo(updateAddressCode);
        updateAll.setMemberLoginPW(updatePW);
        memberMybatisRepository.updateMember(updateAll);

        Member updateMember = memberMybatisRepository.findById(testMember.getMemberLoginID());
        assertThat(updateMember.getAddressCodeNo()).isEqualTo(updateAddressCode);
        assertThat(updateMember.getMemberLoginPW()).isEqualTo(updatePW);
    }

    private Member getTestMapper(){
        Member testMember = new Member();
        testMember.setMemberLoginID("testCode");
        testMember.setMemberLoginPW("Test1234");
        testMember.setAddressCodeNo(1);
        return testMember;
    }
}
