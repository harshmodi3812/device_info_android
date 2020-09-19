
class AndroidDeviceInfo {

  final String simCardDetails;

  AndroidDeviceInfo({this.simCardDetails});

  static AndroidDeviceInfo fromMap(Map<String, dynamic> map) {
   return AndroidDeviceInfo(
     simCardDetails: map['simCardDetails']
   );
  }
}