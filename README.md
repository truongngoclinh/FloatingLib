# FloatingViewLib
Provide a floating view on top of all applications and specific click function as opening a webview.
## Installation
Clone and import into your project.
## Usage
**Provide** `UrlBuilder` object with your `accessToken`.
This class also supports to add more info such as: 
  * `homeUrl`: url of the support page
  * `extras`: extra future params for url
  * `salt`: salt key
  * `redirectUrl`: redirect url
```ruby
FloatingViewManager.UrlBuilder builder = FloatingViewManager.UrlBuilder.newBuilder()
                        .accessToken(FVConstantValue.URL_VALUE.TOKEN)
                        .build();
```              
**Call API** `showView()` from `FloatingViewManager` to show the view, remeber to `hideView()` when it is unnecessary.
```ruby
FloatingViewManager.showView(TestLibActivity.this, builder);
FloatingViewManager.hideView(TestLibActivity.this);
```
Provide `ViewBuilder` object if you need to **custom your floating view**
```ruby
FloatingViewManager.ViewBuilder viewBuilder = FloatingViewManager.ViewBuilder.newBuilder()
                        .drawable(R.drawable.ic_help)
                        .height(150).width(150)
                        .horizontalMargin(30).verticalMargin(50)
                        .onTop(true).onLeft(true)
                        .build();
```
## Contributing
Its me :+1:
## History
*22-11-2016: add source code. v1*
