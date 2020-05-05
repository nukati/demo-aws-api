package com.demo.api.constant;

public interface IErrMessage {
    public final String MSG_ERR_001       = "E001"; //ไม่พบประเภท Key ในการ Encrypt
    public final String MSG_ERR_002       = "E002"; //ไม่พบ file ในการ Encrypt
    public final String MSG_ERR_003       = "E003"; //เกิดข้อผิดพลาด ในการ Encrypt หรือข้อมูลไม่ครบ
    public final String MSG_ERR_004       = "E004"; //ไม่พบ User นี้ในการใช้งานระบบ Dashboard
    public final String MSG_ERR_005       = "E005"; //ยังไม่มีการ Assing กลุ่มให้กับ user นี้
}
