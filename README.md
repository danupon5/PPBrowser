# PPBrowser

เว็บเบราว์เซอร์สำหรับ Android และ Android Auto (ดูวิดีโอบนจอรถตอนจอด) ใช้ส่วนตัว แจกผ่าน **Google Play Internal testing** เท่านั้น

PPBrowser เป็นเวอร์ชันดัดแปลงจาก [RoadBrowser](https://github.com/Yash-v-maurya/RoadBrowser) ของ Yash V Maurya เผยแพร่ภายใต้ **GNU GPLv3** (ดู [LICENSE](LICENSE) และ [docs/legal/notices.md](docs/legal/notices.md))

> [!WARNING]
> **ใช้ตอนจอดรถเท่านั้น** ห้ามดูหน้าจอขณะขับรถ Android Auto จะบังหน้าเบราว์เซอร์เมื่อรถเริ่มวิ่ง แต่เสียงยังเล่นต่อได้

## สิ่งที่เปลี่ยนจาก RoadBrowser

- ชื่อแอปเป็น PPBrowser และ package name เป็น `com.danupon.ppbrowser`
- ลบระบบดาวน์โหลดหรืออัปเดตแอปจาก GitHub (`REQUEST_INSTALL_PACKAGES`) ออก เพราะ Google Play ไม่อนุญาต ตอนนี้ปุ่ม "Check for Updates" จะเปิดหน้า Google Play แทน
- ลบไลบรารี FreeDroidWarn และ repository JitPack ออก
- เปลี่ยน build ให้ออกเป็นไฟล์ AAB ที่เซ็นแล้วสำหรับ Google Play (workflow ชื่อ "Release (Google Play AAB)")
- หน้า Terms, Privacy และ Notices แก้ให้ตรงกับการแจกผ่าน Google Play และยังให้เครดิตผู้เขียนเดิม
- เริ่มนับเวอร์ชันใหม่ที่ `1.0.0` (versionCode 1)

## วิธี build และอัปโหลดขึ้น Google Play

### ครั้งแรก: ตั้งค่า GitHub secrets
ไปที่ repo **Settings → Secrets and variables → Actions → New repository secret** แล้วเพิ่ม 4 ค่า:

| Secret | ค่า |
|---|---|
| `RELEASE_KEYSTORE_BASE64` | ไฟล์ upload keystore (`.jks`) ที่แปลงเป็น base64 |
| `RELEASE_STORE_PASSWORD` | รหัสผ่าน keystore |
| `RELEASE_KEY_ALIAS` | `ppbrowser-upload` |
| `RELEASE_KEY_PASSWORD` | รหัสผ่าน key |

**ห้าม commit ไฟล์ keystore หรือรหัสผ่านลง git** (`.gitignore` กัน `*.jks` และ `*.keystore` ไว้แล้ว)

### Build ไฟล์ AAB
1. ไปที่แท็บ **Actions → Release (Google Play AAB) → Run workflow**
2. รอให้เสร็จ แล้วดาวน์โหลด artifact ชื่อ **PPBrowser-play-bundle** (ได้ไฟล์ zip ที่มี `PPBrowser-<version>-<code>.aab` อยู่ข้างใน)

### อัปโหลดเข้า Internal testing (ครั้งแรก)
1. ใน Play Console กด **Create app** ตั้งชื่อ PPBrowser เลือก App, Free
2. ไปที่ **Testing → Internal testing → Testers** สร้าง email list แล้วใส่อีเมล Google ของคุณและของแฟน
3. **Create new release** ระบบจะถามเรื่อง Play App Signing ให้กด **ใช้ Google-generated key** (ค่าเริ่มต้น) แล้วอัปโหลดไฟล์ `.aab`
4. กรอกส่วน **App content** ให้ครบ:
   - **Privacy policy:** ต้องมี URL สาธารณะ เพราะแอปขอสิทธิ์ไมโครโฟนและตำแหน่ง ใช้เนื้อหาจาก [docs/legal/privacy.md](docs/legal/privacy.md) ไปลงที่ Google Sites หรือหน้าเว็บสาธารณะใดก็ได้
   - **Data safety:** แอปไม่เก็บและไม่ส่งข้อมูลผู้ใช้ไปที่ใด
   - **Ads:** ไม่มีโฆษณา
   - **Target audience:** 18 ปีขึ้นไป
   - กรอก **Content rating** questionnaire ให้ครบ
5. กด **Review release → Start rollout to Internal testing**
6. คัดลอก **opt-in link** จากแท็บ Testers ส่งให้ตัวเองและแฟน เปิดลิงก์บนมือถือด้วยบัญชีที่อยู่ในรายชื่อ กด Accept แล้วติดตั้งจาก Play Store

**อย่ากด promote ไป Production** แอปจะยังเป็นส่วนตัวอยู่ตราบที่อยู่แค่ใน Internal testing

### ออกเวอร์ชันใหม่
1. แก้ `versionCode` (ต้องเพิ่มขึ้นทุกครั้ง) และ `versionName` ใน `app/build.gradle.kts`
2. รัน workflow **Release (Google Play AAB)** อีกครั้ง
3. ใน Play Console ไปที่ Internal testing → **Create new release** แล้วอัปโหลดไฟล์ `.aab` ใหม่ มือถือทุกเครื่องในรายชื่อจะได้อัปเดตผ่าน Play Store

## ให้ PPBrowser ขึ้นใน Android Auto

PPBrowser ไม่ใช่แอป Android Auto ที่ Google อนุมัติ จึงอาจต้องเปิด **Unknown sources** ใน Android Auto ก่อน:

1. บนมือถือ เปิด **Settings** แล้วค้นหา **Android Auto**
2. เลื่อนลงล่างสุด แตะ **Version** 10 ครั้ง แล้วกด OK
3. กดเมนู ⋮ → **Developer settings** → ติ๊ก **Unknown sources**
4. ใน Android Auto ไปที่ **Customize launcher** แล้วติ๊ก PPBrowser
5. ถอดสายแล้วเสียบใหม่ (หรือ force stop Android Auto)

ในแอปก็มี checklist นี้อยู่ที่ **Settings → Android Auto and help → Android Auto setup** ส่วนรายละเอียดเต็มดูได้ที่ [docs/legal/android-auto-setup.md](docs/legal/android-auto-setup.md)

## Build เองบนเครื่อง

ต้องมี **JDK 21** และ Android SDK **platform 37**

```bash
./gradlew assembleDebug    # APK สำหรับทดสอบ
./gradlew bundleRelease    # AAB ที่เซ็นแล้ว (ต้องตั้ง RELEASE_* ใน local.properties หรือ environment ก่อน)
./gradlew test             # unit tests
```

## License

GPLv3 ถ้าส่งแอปให้คนอื่นใช้ ต้องให้ซอร์สโค้ดกับคนนั้นได้เมื่อขอ ฟอนต์ Overpass ใช้ SIL OFL 1.1 ([docs/licenses/OFL_Overpass.txt](docs/licenses/OFL_Overpass.txt))
