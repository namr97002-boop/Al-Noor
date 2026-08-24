# المسلم الذكي - Android

هذا مشروع Android مبدئي لتغليف واجهة HTML/Vue وتشغيل الأذان من طبقة Android الأصلية.

ضع:
- index_final_fixed.html في app/src/main/assets/
- service-worker.js في app/src/main/assets/
- manifest.json في app/src/main/assets/
- adhan.mp3 في app/src/main/res/raw/adhan.mp3

ثم ابنِ المشروع باستخدام:
./gradlew assembleDebug

ملاحظة: يجب مراجعة مواصفات Android الحديثة، الصلاحيات، ودقة جدولة المواقيت قبل إصدار نسخة نهائية.
